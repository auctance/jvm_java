package classpath;

import java.io.IOException;

public abstract class Entry {
    public static final String pathListSeparator=System.getProperty("os.name").contains("Windows") ? ";":":";
    abstract byte[] readClass(String className) throws IOException;
    abstract String printClassName();

    static Entry createEntry(String path){
        if (path!=null){
            if (path.contains(pathListSeparator)){
                return new CompositeEntry(path,pathListSeparator);
            }else if (path.contains("*")){
                return new WildcardEntry("");
             }else if (path.contains(".jar") || path.contains(".JAR") || path.contains(".zip") || path.contains(""+".ZIP")){
                return new ZipJarEntry(path);
            }
            return new DirEntry(path);
        }
        return null;
    }

}
