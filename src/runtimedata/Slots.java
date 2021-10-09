package runtimedata;

import runtimedata.heap.Zobject;

// 针对静态变量表 申请空间之后 如果没有显示赋值 那么应该为0或者null
// 而slot的构造方法没有实现该功能
// 在此封装 Slot[]创建完成之后 为其每一个元素创建一个slot对象
// 这样 在没有为静态变量赋值时， Slot[]元素被访问不会空指针异常
public class Slots {
    private Slot[] slots;

    // Slot[] 中的每个元素
    // 都有一个Slot数据结构
    public Slots(int size){
        slots = new Slot[size];
        for (int i=0;i <size;i++){
            slots[i] = new Slot();
        }
    }

    // slots作为基本数据结构单元
    // 实现对int float long doule的存取
    public void setInt(int index, int val){
        slots[index].num=val;
    }

    public int getInt(int index){
        return slots[index].num;
    }

    public void setFloat(int index, float val){
        slots[index].num=Float.floatToRawIntBits(val);
    }

    public float getFloat(int index){
        return Float.intBitsToFloat(slots[index].num);
    }
    public void setLong(int index, long val){
        slots[index].num=(int) (val);
        slots[index+1].num =(int) (val>>32);
    }

    public long getLong(int index){
        int low = slots[index].num;
        int high = slots[index+1].num;
        return ((high & 0x000000ffffffffL) << 32) | (low & 0x00000000ffffffffL);
    }
    public void setDouble(int index, double val){
        long bits = Double.doubleToLongBits(val);
        setLong(index,bits);
    }

    public double getDouble(int index){
        long bits = getLong(index);
        return Double.longBitsToDouble(bits);
    }
    public void setRef(int index, Zobject ref){
        slots[index].ref=ref;
    }

    public Zobject getRef(int index){
        return slots[index].ref;
    }

    public void setSlot(int index, Slot slot){
        slots[index] = slot;
    }
}
