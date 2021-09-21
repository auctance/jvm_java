package runtimedata.heap;

import classpath.ClassPath;

import java.util.HashMap;

public class ZclassLoader {
    ClassPath classPath;
    HashMap<String, Zclass> map;

    public ZclassLoader(ClassPath classPath){
        this.classPath=classPath;
        this.map=new HashMap<String,Zclass>();

        loadBasicClasses();
        loadPrimitiveClasses();
    }

    private void loadBasicClasses(){
        Zclass
    }
}
