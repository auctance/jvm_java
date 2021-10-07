package classpath;
// 寻找class文件的具体方法
// 标准路径情况

import com.sun.tools.corba.se.idl.StringGen;
import com.sun.xml.internal.xsom.impl.scd.Step;

import java.io.*;
import java.nio.Buffer;

public class DirEntry extends Entry{
    // 判断路径是否存在 如果存在和classname拼接 使用IO读取其中的字节码 并且返回
    private String  absDir;
    // 构造方法 返回绝对路径 便于和classname拼接
    public DirEntry(String path){
        File dir = new File(path);
        if (dir.exists()) absDir = dir.getAbsolutePath();
    }
    // 重写抽象Entry中的读出class文件 的方法
    @Override
    byte[] readClass(String className) throws IOException{
        File file = new File(absDir,className);
        //
        if (!file.exists()){
            return null;
        }
        byte[] tmp = new byte[1024];
        BufferedInputStream in = null;
        ByteArrayOutputStream out = null;
        in = new BufferedInputStream(new FileInputStream(file));
        out = new ByteArrayOutputStream(1024);

        // 读文件
        int size=0;
        while((size=in.read(tmp))!=-1){
            out.write(tmp,0,size);
        }
        if (in != null) in.close();
        if (out != null) out.close();
        return out.toByteArray();

    }
    @Override
    // 打印classpath
    String printClassName(){return absDir;}

}