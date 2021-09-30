
// 常量池的抽象类 实现了常量池中所有常量的类型
// 常量数据的第一字节是tag 用来区分常量类型
//

import classfile.ClassReader;
import classfile.ConstantPool;

public abstract class ConstantInfo{
    public static final int CONSTANT_Utf8 = 1;
    public static final int CONSTANT_Integer = 3;
    public static final int CONSTANT_Float = 4;
    public static final int CONSTANT_Long = 5;
    public static final int CONSTANT_Double = 6;
    public static final int CONSTANT_Class = 7;
    public static final int CONSTANT_String = 8;
    public static final int CONSTANT_Fieldref = 9;
    public static final int CONSTANT_Methodref = 10;
    public static final int CONSTANT_InterfaceMethodref = 11;
    public static final int CONSTANT_NameAndType = 12;
    public static final int CONSTANT_MethodHandle = 15;
    public static final int CONSTANT_MethodType = 16;
    public static final int CONSTANT_InvokeDynamic = 18;

    // 抽象方法读取信息 每种常量占用的字节数不同 需要具体实现
    abstract void  readInfo(ClassReader reader);

    // 表明常量类型是 上述的哪一种常量
    protected int type;
    public int getType(){return type;}

    // 读取常量信息 读取tag信息 根据tag实现不同的常量类 并且添加到常量池数组中
    private static ConstantInfo readConstantInfo(ClassReader reader, ConstantPool constantPool){
        int type=(reader.readUint8()+256)%256;
        ConstantInfo info = create(type,constantPool);
        info.readInfo(reader);
        return info;
    }

    // 从常量池中获取 确定类型 创建常量信息
    private static ConstantInfo create(int type,ConstantPool constantPool){
        switch (type) {
            case CONSTANT_Utf8:
                return new ConstantUtf8Info(1);
            case CONSTANT_Integer:
                return new ConstantIntegerInfo(3);
            case CONSTANT_Float:
                return new ConstantFloatInfo(4);
            case CONSTANT_Long:
                return new ConstantLongInfo(5);
            case CONSTANT_Double:
                return new ConstantDoubleInfo(6);
            case CONSTANT_String:
                return new ConstantStringInfo(constantPool, 8);
            case CONSTANT_Class:
                return new ConstantClassInfo(constantPool, 7);
            case CONSTANT_Fieldref:
                return new ConstantFieldRefInfo(constantPool, 9);
            case CONSTANT_Methodref:
                return new ConstantMethodRefInfo(constantPool, 10);
            case CONSTANT_InterfaceMethodref:
                return new ConstantInterfaceMethodRefInfo(constantPool, 11);
            case CONSTANT_NameAndType:
                return new ConstantNameAndTypeInfo(12);
            case CONSTANT_MethodType:
                return new ConstantMethodTypeInfo(16);
            case CONSTANT_MethodHandle:
                return new ConstantMethodHandleInfo(15);
            case CONSTANT_InvokeDynamic:
                return new ConstantInvokeDynamicInfo(18);
            default:
                throw new RuntimeException("java.lang.ClassFormatError: constant pool tag!");
        }
    }


}