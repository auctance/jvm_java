package runtimedata;

public class LocalVars {
    private Slot[] localVars;

    public LocalVars(int maxLocals){
        if (maxLocals>0) localVars=new Slot[maxLocals];
        else throw new NullPointerException("maxLocals < 0");
    }

    public void setInt(int index,int val){
        Slot slot=new Slot();
        slot.num=val;
        localVars[index]=slot;
    }

    public int getInt(int idnex){return localVars[index].num;}

    public void setLong(int index, long val){
        Slot slot1 = new Slot();
        slot1.num=(int) (val);
        localVars[index] = slot1;
        Slot slot2 =new Slot();
        slot2.num=(int) (va>>32);
        localVars[index+1]=slot2;
    }

    public long getLong(int index){
        int low = localVars[index].num;
        long high = localVars[index+1].num;
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
