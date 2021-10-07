package Utils;
// 解析class文件 解析基本类型常量部分
// 将byte 字节 内容解析为int或者string等其他类型
public class ByteUtils {
    // 十六进制的字符串
    public static String bytesToHexString(byte[] src){
        return bytesToHexString(src,src.length);
    }
    // overload
    // bytes to string
    // 每个byte转换一下 用stringbuilder接受
    public static String bytesToHexString(byte[] src, int len){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src==null || len<=0){
            return null;
        }
        for (int i=0;i<len;i++){
            int v = src[i]&0xFF;
            String hv = Integer.toHexString(v).toUpperCase();
            if (hv.length()<2){
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static int bytesToU16(byte[] data){
        assert data.length==2;
        return (data[0]+256)%256*256+(data[1]+256)%256;
    }

    public static int byteToInt32(byte[] data){
        assert data.length==4;
        int res=0;
        for (int i=0;i<data.length;i++){
            res = res<<8 | (data[i]+256)%256;
        }
        return res;
    }

    public static long byteToLong64(byte[] data){
        assert data.length==8;
        long res=0;
        for (int i=0;i <data.length;i++){
            res  =res<<8 | (data[i]+256)%256;
        }
        return res;
    }

    public static float byte2Float32(byte[] b){
        int i=byteToInt32(b);
        return Float.intBitsToFloat(i);

    }
    public static double bytw2Double64(byte[] b){
        long l = byteToLong64(b);
        return Double.longBitsToDouble(l);
    }
}
