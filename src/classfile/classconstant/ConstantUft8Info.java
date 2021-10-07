package classfile.classconstant;

import classfile.ClassReader;

import java.io.IOException;

// class文件中的字符串常量编码为 mutf-8 解析字符串常量时候需要编码转换
public class ConstantUft8Info extends ConstantInfo{
    @Override
    void readInfo(ClassReader reader){
        int len = reader.readUint16();
        byte[] data = reader.readBytes(len);
        try {
            val = decodeMUTF8(data);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private static String decodeMUTF8(byte[] bytearr) throws IOException{
        return new String(chararr,0,chararr_count);
    }
}
