// 这里是线程运行时候 共享区
// 包括方法区和堆

// Zclass 代表共享的类信息
import runtimedata.heap.ZclassLoader;

public class Zclass {
    // 当前类的访问标志
    private int accessFlags;
    // 当前类名字
    public String thisClassName;
    // 父亲类名字
    public String superClassNmme;
    // 接口名字
    public String[] interfaceNames;
    // 运行时常量池
    private RuntimeConstantPool runtimeConstantPool;
    // 字段表
    Zfield[] fields;
    // 方法表
    Zmethod[] methods;
    // 类加载器
    ZclassLoader loader;
    //  父亲类class
    Zclass superClass;
    // 接口类class
    Zclass[] interfaces;
    // 非静态变量slot大小
    int instanceSlotCount;
    // 静态变量空间大小
    int staticSlotCount;
    // 存放静态变量
    Slots staticVars;
}