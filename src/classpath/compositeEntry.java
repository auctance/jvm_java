package classpath;

import java.io.IOException;
import java.util.ArrayList;

public class compositeEntry extends Entry{
    ArrayList<Entry>  compositeEntries;
    private String pathList;
    public CompositeEntry(){

    }
    public CompositeEntry(String pathList, String pathListSeparator){
        this.pathList=pathList;
        String[] paths=pathList.split(pathListSeparator);
        compositeEntries=new ArrayList<Entry>(paths.length);
        for (int i=0; i< paths.length; i++){
            compositeEntries.add(new DirEntry(paths[i]));
        }
    }

    @Override
    byte[] readClass(String className){
        byte[] data;
        for (int i=0; i< compositeEntries.size(); i++){
            try {
                data = compositeEntries.get(i).readClass(className);
                if (data != null) return data;
            }catch (IOException e){
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
