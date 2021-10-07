package classfile.classconstant;

import classfile.ClassReader;
import classfile.ConstantPool;

import java.io.IOException;
import java.io.UTFDataFormatException;

// class文件中的字符串常量编码为 mutf-8 解析字符串常量时候需要编码转换
public class ConstantUtf8Info extends ConstantInfo{
    public String val;
    public ConstantUtf8Info(int i){
        type=i;
    }
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
        int utflen = bytearr.length;
        char[] chararr = new char[utflen];
        int c,char2,char3;
        int count=0;
        int chararr_count=0;
        while (count<utflen){
            c = (int) bytearr[count]&0xff;
            if(c>127){
                break;
            }
            count++;
            chararr[chararr_count++] = (char) c;
        }
        while (count<utflen){
            c=(int) bytearr[count]&0xff;
            switch (c>>4){
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    count++;
                    chararr[chararr_count++] = (char) c;
                    break;
                case 12:
                case 13:
                    count +=2;
                    if (count>utflen){
                        throw new UTFDataFormatException("partitial character at end");
                    }
                    char2 = (int) bytearr[count-1];
                    if ((char2 & 0xC0)!=0x80){
                        throw new UTFDataFormatException();
                    }
                    chararr[chararr_count++] = (char) (((c & 0x1F)<<6)|(char2 & 0x3F));
                    break;
                case 14:
                    count += 3;
                    if (count > utflen) {
                        throw new UTFDataFormatException(
                                "malformed input: partial character at end");
                    }
                    char2 = (int) bytearr[count - 2];
                    char3 = (int) bytearr[count - 1];
                    if (((char2 & 0xC0) != 0x80) || ((char3 & 0xC0) != 0x80)) {
                        throw new UTFDataFormatException(
                                "malformed input around byte " + (count - 1));
                    }
                    chararr[chararr_count++] = (char) (((c & 0x0F) << 12) |
                            ((char2 & 0x3F) << 6) |
                            ((char3 & 0x3F) << 0));
                    break;
                default:
                    /* 10xx xxxx,  1111 xxxx */
                    throw new UTFDataFormatException(
                            "malformed input around byte " + count);
            }
        }
        return new String(chararr,0,chararr_count);
    }

    public String getVal(){
        return val;
    }
}
