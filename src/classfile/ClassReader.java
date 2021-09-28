package classfile;

// 读取class字节码文件的reader 包含一个index 指示当前要读的字符数组的索引

public class ClassReader {
    private byte[] data;
    private int index=0;
    // 将data加载到类变量
    public ClassReader(byte[] data){
        this.data=data;
    }
    // 读int8的整形
    public byte readUint8(){
        byte res = data[index++];
        return res;
    }
    // 无符号的16位整形
    public int readUint16() {
        byte[] res = new byte[2];
        res[0] = data[index++];
        res[1] = data[index++];
        return ByteUtils.bytesToU16(res);
    }

    public byte[] readUint32() {
        byte[] res = new byte[4];
        res[0] = data[index++];
        res[1] = data[index++];
        res[2] = data[index++];
        res[3] = data[index++];
        return res;
    }

    public byte[] readUint64() {
        byte[] res = new byte[8];
        res[0] = data[index++];
        res[1] = data[index++];
        res[2] = data[index++];
        res[3] = data[index++];
        res[4] = data[index++];
        res[5] = data[index++];
        res[6] = data[index++];
        res[7] = data[index++];
        return res;
    }

    // 读取连续的16bit长的数组
    public int[] readUint16s() {
        int n = readUint16();
        int[] data = new int[n];
        for (int i = 0; i < n; i++) {
            data[i] = readUint16();
        }
        return data;
    }

    public byte[] readBytes(int n) {
        byte[] res = new byte[n];
        for (int i = 0; i < n; i++) {
            res[i] = data[index++];
        }
        return res;
    }
}