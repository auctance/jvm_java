package runtimedata;

public class OperandStack {
    private int size;
    private Slot[] slots;

    public OperandStack(int maxStack){
        if(maxStack>0) slots=new Slot[maxStack];
        else throw new NullPointerException("maxStack < 0");
    }

    public void pushInt(int val){
        Slot slot=new Slot();
        slot.num=val;
        slots[size] = slot;
        size++;
    }

    public int popInt(){
        size--;
        return slots[size].num;
    }
}
