// 利用slot容器 存储局部变量（虚拟机栈的栈帧包括局部变量以及操作数）

// 局部变量储存 一个栈帧中的方法（函数）运行时候的变量数据

public class LocalVars {
    // 局部变量表按照索引访问  所以设置为数组类型
    private Slot[] localVars;
    // 调用构造方法初始化 localvars
    public LocalVars(int maxLocals){
        if (maxLocals>0){
            localVars = new Slot[maxLocals];
        }
        else{
            throw new NullPointerException("maxLocals<0");
        }

    }

    // 对int  float long double类型的存取
    public void setInt(int index, int val){
        Slot slot = new Slot();
        slot.num = val;
        localVars[index]= slot;
    }

    public int getInt(int index){
        return localVars[index].num;
    }

    public void setFloat(int index, float val) {
        Slot slot = new Slot();
        slot.num = Float.floatToIntBits(val);
        localVars[index] = slot;
    }

    public float getFloat(int index) {
        return Float.intBitsToFloat(localVars[index].num);
    }

    public void setLong(int index, long val) {
        //先存低 32 位
        Slot slot1 = new Slot();
        slot1.num = (int) (val);
        localVars[index] = slot1;
        //再存高 32 位
        Slot slot2 = new Slot();
        slot2.num = (int) (val >> 32);
        localVars[index + 1] = slot2;
    }

    public long getLong(int index) {
        int low = localVars[index].num;
        long high = localVars[index + 1].num;
        return ((high & 0x000000ffffffffL) << 32) | (low & 0x00000000ffffffffL);
    }

    public void setDouble(int index, double val) {
        long bits = Double.doubleToLongBits(val);
        setLong(index, bits);
    }

    public double getDouble(int index) {
        long bits = getLong(index);
        return Double.longBitsToDouble(bits);
    }

    public void setRef(int index, Zobject ref) {
        Slot slot = new Slot();
        slot.ref = ref;
        localVars[index] = slot;
    }

    public Zobject getRef(int index) {
        return localVars[index].ref;
    }
}