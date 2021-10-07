
// 已经实现了对不同classpath的解析 这里是封装 对外提供一个统一的接口
package classpath;

import classpath.Entry;
import classpath.WildcardEntry;

import java.io.File;
import java.io.IOException;

public class ClassPath {
    // jre 路径
    private String jreDir;
    // 三种类型的路径
    private Entry bootClasspath;
    private Entry extClasspath;
    private Entry userClasspath;

    // 使用 -xjre 选项解析 启动类和扩展类路径
    // 使用-cp 选项解析用户类路径
    public ClassPath(String jreOption, String cpOption){
        jreDir = getJreDir(jreOption);
        bootClasspath=parseBootClassPath();
        extClasspath=parseExtClassPath();
        userClasspath = parseUserClassPath(cpOption);
    }

    // 解析 boot 和 ext 类路径
    private Entry parseBootClassPath(){
        String jreLibPath = jreDir+ File.separator+"lib"+File.separator+"*";
        return new WildcardEntry(jreLibPath);
    }
    private Entry parseExtClassPath(){
        String jreExtPath = jreDir+File.separator+"lib"+File.separator+"ext"+File.separator+"*";
        return new WildcardEntry(jreExtPath);
    }


//    void parseBootAndExtClassPath(String jreOption) {
//        String jreDir=getJreDir(jreOption);
//        // jre/lib/*  jre/lib/ext/*
//        String jreLibPath = jreDir+ File.separator+"lib"+File.separator+"*";
//        bootClasspath=new WildcardEntry(jreLibPath);
//        String jreExtPath = jreDir+File.separator+"lib"+File.separator+"ext"+File.separator+"*";
//        extClasspath=new WildcardEntry(jreExtPath);
//    }

    // 获取jre目录 判断目录是否有效
    private String getJreDir(String jreOption) {
        File jreFile;
        if (jreOption!=null && jreOption!=""){
            jreFile=new File(jreOption);
            if (jreFile.exists()) return jreOption;
        }
        // 如果jreOption选项为空 那么在当前路径去寻找
        jreFile = new File("jre");
        if (jreFile.exists()) return jreFile.getAbsolutePath();
        // 在JAVA HOME中寻找
        String java_home = System.getenv("JAVA_HOME");
        if (java_home!=null) return java_home+File.separator+"jre";
        throw new RuntimeException("can not find jre folder!");
    }

    // 解析用户类class 路径
    private Entry parseUserClassPath(String cpOption){
        return Entry.createEntry(cpOption);
    }


    // 从不同类路径尝试加载类
    // classfile的查找顺序是 boot ext user
    public byte[] readClass(String className){
        if (className.endsWith(".class")){
            throw new RuntimeException();
        }
        className = className.replace(".","/");
        className=className+".class";
        byte[] data;
        try{
            data = bootClasspath.readClass(className);
            if (data!=null) return data;
            data = extClasspath.readClass(className);
            if (data!=null) return data;
            data = userClasspath.readClass(className);
            if (data!=null) return data;
        }catch (IOException e){
            e.printStackTrace();
        }
        throw new RuntimeException("can not find class!");
    }
    @Override
    public String toString(){
        return userClasspath.printClassName();
    }

}