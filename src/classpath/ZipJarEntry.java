package classpath;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

// 读取zip或者jar文件夹中的class文件
public class ZipJarEntry extends Entry{
    String absPath; // 绝对路径 例如 /p/a/t/h/test.jar
    String zipName; // 压缩包的名字 例如 test 不带.jar .zip
    // 可能输入了路径
    public ZipJarEntry(String path){
        File dir = new File(path);
        if (dir.exists()){
            absPath = dir.getParentFile().getAbsolutePath();
            zipName = dir.getName();
            zipName = zipName.substring(0,zipName.length()-4);

        }
    }
    // 重载 输入的可能是带类名字
    public ZipJarEntry(String path,String zipName){
        File dir = new File(path,zipName);
        if (dir.exists()){
            absPath=dir.getAbsolutePath();
            this.zipName=zipName.substring(0,zipName.length()-4);
        }
    }

    // 从zip或者jar文件中读取class文件
    @Override
    byte[] readClass(String className) throw IOException{
        File file = new File(absPath);
        ZipInputStream zin = null;
        BufferedInputStream in = null;
        ByteArrayOutputStream out = null;
        ZipFile zf = new ZipFile(file);
        ZipEntry ze = zf.getEntry(className);
        if (ze==null) return null;
        in = new BufferedInputStream(zf.getInputStream(ze));
        out = new ByteArrayOutputStream(1024);
        int size=0;
        byte[] temp = new byte[1024];
        // 缓冲读取
        while ((size=in.read(temp))!=-1){
            out.write(temp,0,size);
        }
        if (zin!=null) zin.closeEntry();
        if (in!=null) in.close();
        if (out!=null) out.close();
        return out.toByteArray();
    }
    @Override
    String printClassName(){
        return absPath;
    }

}
