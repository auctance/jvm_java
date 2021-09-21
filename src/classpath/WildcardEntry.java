package classpath;

import java.io.File;
import java.util.ArrayList;

public class WildcardEntry extends Entry{
    public CompositeEntry compositeEntry;

    public WildcardEntry(String jreLibPath){
        String baseDir = jreLibPath.substring(0,jreLibPath.length()-1);
        File dir =  new File(baseDir);
        File[] files = dir.listFiles();
        compositeEntry = new CompositeEntry();
        compositeEntry.compositeEntries=new ArrayList<Entry>();
        for (File file : files){
            if (file.isFile() && file.getName().endsWith(".jar")){
                compositeEntry.compositeEntries.add(new ZipJarEntry(baseDir,file.getName()));
            }
        }
//        System.out.println(compositeEntry。compositeEntries.);

    }

    @Override
    String printClassName() {
        return null;
    }
    @Override
    byte[] readClass(string className){
        return compositeEntry.readClass(className);
    }
}
