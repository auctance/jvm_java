package classpath;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

// 多个路径
// 将路径分割 对每个路径使用Entry实例化

public class CompositeEntry extends Entry{
    // 多个entry
    ArrayList<Entry> compositeEntries;

    // 多个路径的list
    private String pathList;

    public CompositeEntry(){
    }
    public CompositeEntry(String pathList,String pathListSeparator){
        this.pathList=pathList;
        // 储存路径 strings
        String[] paths = pathList.split(pathListSeparator);
        // 每个路径 调用DirEntry方法 得到一个Entry
        compositeEntries = new ArrayList<Entry>(paths.length);
        for (int i=0; i< paths.length;i++){
            compositeEntries.add(new DirEntry(paths[i]));
        }
    }
    @Override
    // 对每个entry readClass
    byte[] readClass(String className){
        byte[] data;
        for (int i=0; i<compositeEntries.size(); i++){
            try {
                data = compositeEntries.get(i).readClass(className);
                if (data!=null) return data;
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return null;
    }
    @Override
    String printClassName(){
        return pathList;
    }
}