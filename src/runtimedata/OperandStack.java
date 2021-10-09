package runtimedata;

// 栈帧中的操作数栈
// 大小在编译期间已经确定 可以用slot数组实现
// 是用数组模拟的栈 对外接口需要是栈的形式
public class OperandStack {
    private int size;
    private Slot[] slots;

    public OperandStack(int maxStack){
        if (maxStack>0){
            slots = new Slot[maxStack];
        }else{
            throw new NullPointerException("maxStack<0");
        }
    }

    public void pushBoolean(boolean val){
        if (val) {pushInt(1);}
        else{pushInt(0);}
    }

    public boolean popBoolean(){
        return popInt()==1;
    }
    public void pushInt(int val){
        Slot slot = new Slot();
        slot.num = val;
        // size is current index
        slots[size] = slot;
        size++;
    }

    public int popInt(){
        size--;
        return slots[size].num;
    }

    public void pushFloat(float val){
        Slot slot = new Slot();
        slot.num = Float.floatToRawIntBits(val);
        slots[size]  =slot;
        size++;
    }

    public float popFloat(){
        size--;
        return Float.intBitsToFloat(slots[size].num);
    }

    public void pushLong(long val){
        Slot slot1 =  new Slot();
        slot1.num = (int) (val);
        slots[size] = slot1;
        size++;
        Slot slot2 = new Slot();
        slot2.num = (int)(val>>32);
        slots[size] = slot2;
        size++;
    }

    public long popLong(){
        size-=2;
        int low = slots[size].num;
        int high = slots[size+1].num;
        return ((high & 0x000000ffffffffL) << 32) | (low & 0x00000000ffffffffL);
    }

    public void pushDouble(double val) {
        long bits = Double.doubleToLongBits(val);
        pushLong(bits);
    }

    public double popDouble() {
        long bits = popLong();
        return Double.longBitsToDouble(bits);
    }

    public void pushRef(Zobject ref) {
        Slot slot = new Slot();
        slot.ref = ref;
        slots[size] = slot;
        size++;
    }

    public Zobject popRef() {
        size--;
        return slots[size].ref;
    }

    public void pushSlot(Slot slot) {
        slots[size] = slot;
        size++;
    }

    public Slot popSlot() {
        size--;
        return slots[size];
    }

    //新添加的方法,根据参数n,返回操作数栈中的倒数第n个引用;
    public Zobject getRefFromTop(int n) {
        return slots[size - 1 - n].ref;
    }

    //just for test!
    public boolean isEmpty() {
        return size == 0;
    }

    //清空操作数栈，直接将 size 设置为0，而不是将所有的 slot 都设为 null；因为这样可能会引起其它问题
    public void clear() {
        size = 0;
    }
}
