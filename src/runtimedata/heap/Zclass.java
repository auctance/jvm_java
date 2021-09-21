package runtimedata.heap;

public class Zclass {
    private int accessFlags;
    public String thisClassName;
    public String superClassName;
    public String[] interfaceNames;//接口名字(完全限定名,不可以为null,若为实现接口,数组大小为0)
    private RuntimeConstantPool runtimeConstantPool;//运行时常量池,注意和class文件中常量池区别;
    Zfield[] fileds;        //字段表,包括静态和非静态，此时并不分配 slotId；下面的staticVars 是其子集
    Zmethod[] methods;      //方法表，包括静态和非静态
    ZclassLoader loader;    //类加载器
    Zclass superClass;      //当前类的父类class,由类加载时,给父类赋值;
    Zclass[] interfaces;    //当前类的接口class,由类加载时,给父类赋值;
    int instanceSlotCount;  //非静态变量占用slot大小,这里只是统计个数(从顶级父类Object开始算起)
    int staticSlotCount;    // 静态变量所占空间大小
    Slots staticVars;      // 存放静态变量
}
