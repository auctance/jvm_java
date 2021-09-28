package classpath;

import java.io.File;
import java.util.ArrayList;

// 读取模糊路径中的class文件
// 首先将路径末尾的*去掉 然后遍历所有父路径下的文件 寻找class 文件

public class WildcardEntry extends Entry{
    public CompositeEntry compositeEntry;
    public WildcardEntry(String jreLibPath){
        String baseDir = jreLibPath.substring(0,jreLibPath.length()-1);
        File dir = new File(baseDir);
        // 该目录下的所有文件
        File[] files = dir.listFiles();
        compositeEntry = new CompositeEntry();
        compositeEntry.compositeEntries=new ArrayList<Entry>();
        for (File file:files){
            if (file.isFile() && file.getName().endsWith(".jar")){
                compositeEntry.compositeEntries.add(new ZipJarEntry(baseDir,file.getName()));
            }
        }

    }
    @Override
    byte[] readClass(String className){
        return compositeEntry.readClass(className);
    }
    @Override
    String printClassName(){
        return null;
    }
}


