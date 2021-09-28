package classpath;

import java.io.File;
import java.io.IOException;

// provide a union interface for outer
public class ClassPath {
    Entry bootClasspath;
    Entry extClasspath;
    Entry userClasspath;

    public ClassPath(String jreOption, String cpOption){
        parseBootAndExtClasspath(jreOption);
        parseUserClasspath(cpOption);
    }

    void parseBootAndExtClasspath(String jreOption){
        String jreDir = getJreDir(jreOption);
        String jreLibPath = jreDir+ File.separator+"lib"+File.separator+"*";
        bootClasspath=new WildcardEntry(jreLibPath);
        String jreExtPath = jreDir+ File.separator+"lib"+File.separator+"ext";
        extClasspath=new WildcardEntry(jreExtPath);
    }
    String getJreDir(String jreOption){
        File jreFile;
        if (jreOption!= null && jreOption!=""){
            jreFile=new File(jreOption);
            if (jreFile.exists()) return jreOption;
        }

        jreFile=new File("jre");
        if (jreFile.exists()) return jreFile.getAbsolutePath();

        String java_home=System.getenv("JAVA_HOME");
        if(java_home!=null) return java_home+File.separator+"jre";
        throw  new RuntimeException("can not find jre folder!");
    }

    void parseUserClasspath(String cpOption){
        userClasspath=Entry.createEntry(cpOption);
    }

    public byte[] readClass(String className){
        className = className+".class";
        byte[] data;
        try {
            data = bootClasspath.readClass(className);
            if (data!=null) return data;
            data = extClasspath.readClass(className);
            if (data!=null) return data;
            data = userClasspath.readClass(className);
            if (data!=null) return data;
        }catch (IOException e){
            e.printStackTrace();
        }
        throw new RuntimeException("can not find class");
    }
    @Override
    public String toString(){
        return userClasspath.printClassName();
    }
}
