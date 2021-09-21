package classpath;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class DirEntry extends Entry{
    String absDir;

    public DirEntry(String path){
        File dir = new File(path);
        if (dir.exists()){
            absDir = dir.getAbsolutePath();
        }
    }

    @Override
    byte[] readClass(String className) throws IOException {
        File file = new File(absDir, className);
        byte[] temp = new byte[1024];
        BufferedInputStream in = null;
        ByteArrayOutputStream out=null;
        in = new BufferedInputStream(new FileInputStream(file));
        out = new ByteArrayOutputStream(1024);
        int size =0;
        while((size = in.read(temp)) != -1){
            out.write(temp,0,size);
        }
        if ( in != null){
            in.close();
        }
        if (out != null){
            out.close();
        }
        return out.toByteArray();
    }

    @Override
    String printClassName() {
        return absDir;
    }
}
