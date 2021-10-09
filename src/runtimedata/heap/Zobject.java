package runtimedata.heap;
// 这里定义一个类 用于放索引
public class Zobject {
    private Zclass clazz;
    private Object data;
    public Object extra;

    public Zobject(Zclass clazz){
        this.clazz = clazz;
        data =new Slots(clazz.instanceSlotCount);

    }
}
