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

    //
    Zobject jObject;

    // 构造方法 用于直接创数组类型
    public Zclass(int accessFlags, String thisClassName, ZclassLoader loader,
                  boolean initStarted, Zclass superClass, Zclass[] interfaces
                  ){
        this.accessFlags = accessFlags;
        this.thisClassName = thisClassName;
        this.loader = loader;
        this.initStarted = initStarted;
        this.superClass = superClass;
        this.interfaces = interfaces;
    }

    // 创建数组对象
    public Zobject newArray(int count){
        if (!isArray()){
            throw new RuntimeException();
        }
        switch (thisClassName) {
            case "[Z":
                return new Zobject(this, new byte[count], null);
            case "[B":
                return new Zobject(this, new byte[count], null);
            case "[C":
                return new Zobject(this, new char[count], null);
            case "[S":
                return new Zobject(this, new short[count], null);
            case "[I":
                return new Zobject(this, new int[count], null);
            case "[J":
                return new Zobject(this, new long[count], null);
            case "[F":
                return new Zobject(this, new float[count], null);
            case "[D":
                return new Zobject(this, new double[count], null);
            default:
                return new Zobject(this, new Zobject[count], null);
        }
    }
}