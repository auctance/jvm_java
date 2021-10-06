// 类加载器 将class文件加载到内存

//  加载 验证 准备 初始化 卸载
// 类的解析在初始化之前
// 如果是多态 在初始化之后解析

// 类未初始化 遇到以下五种情况 会立即初始化
// 对于已经初始化的类 从方法区中读取


// 1。 遇到new getstatic putstatic invokestatic 使用new实例化对象  读写类的静态变量（static final不需要初始化）
// 2。 使用反射 调用类
// 3。 初始化一个类 发现父类未初始化 对fulei先初始化
// 4。 启动虚拟机时候 需要制定一个包含main方法的类 虚拟机会先初始化该类
// 5。 如果一个methodhandle实例最后的解析结果是 REF_getStatic REF_putStatic REF_invokeStatic 方法的句炳 会触发对应的REF类的加载


import runtimedata.heap.ClassFile;

import java.util.zip.ZipFile;

public class ZclassLoader {


// 类的全限定名：将。替换为/ 作为唯一确定的位置的一种写法
// 加载阶段虚拟机需要做两件事情
// 1。 通过类的全限定名来获取类的二进制字节流 寻找类所在路径并读取class文件 映射为classFile对象
// 2。 将字节流代表的静态储存结构转孩奴为运行时动态储存结构 将classfile转换为zclass

    // 读取class文件 并且将字节码转换为zclass
    private byte[] readClass(String name){
        byte[] data = classPath.readClass(name);
        return data;
    }
    private Zclass paraseClass(byte[] data){
        ClassFile cf = new ClassFile(data);
        return new Zclass(cf);
    }

    // 验证
    // 确保字节码流符合虚拟机的要求
    private void verify(Zclass clazz){

    }

    // 准备
    // 为静态变量分配内存 并且设置初始值
    // 这里的初始值为类型的零值 并没有完成赋值
    // 如果是final修饰的 直接完成赋值
    private void prepare(Zclass clazz){
        calcInstanceFieldSlotIds(clazz);
        calcStaticFieldSlotIds(clazz);
        allocAndInitStaticVars(clazz);
    }

    // 计算new一个变量需要的空间
    // 这里并没有申请空间 只是计算了大小 为非静态变量关联了slotId
    private void calcInstanceFieldSlotIds(Zclass clazz){
        int slotId = 0;
        if (clazz.superClass!=null){
            slotId = clazz.superClass.instanceSlotCount;
        }
        // 局部变量
        for (Zfield field:clazz.fields){
            if (!field.isStatic()){
                field.slotId = slotId;
                slotId++;
                if (field.isLongOrDouble()){
                    slotId++;
                }
            }
        }
        clazz.instanceSlotCount=slotId;
    }

    // 计算类的静态成员变量所需的空间 不包含父亲类
    // 计算和分配slotId 暂时不分配空间
    private void calcStaticFieldSlotIds(Zclass clazz){
        int slotId = 0;
        for (Zfield field:clazz.fields){
            if(field.isStatic()){
                field.slotId = slotId;
                slotId++;
                if (field.isLongOrDouble){
                    slotId++;
                }
            }
        }
        clazz.instanceSlotCount=slotId;
    }

    // 为静态变量申请空间 即将所有的静态变量赋值为0或者null
    // static final, string 不一样的处理
    private void allocAndInitStaticVars(Zclass clazz){
        clazz.staticVars = new Slots(clazz.staticSlotCount);
        for (Zfield field:clazz.fields){
            if (field.isStatic() && field.isFinal()){
                initStaticFinalVar(clazz,field);
            }
        }
    }


    // 为static final的成员赋值
    private void initStaticFinalVar(Zclass clazz, Zfield zfield){
        Slots staticVars = clazz.staticVars;
        RuntimeConstantPool runtimeConstantPool = clazz.getRuntimeConstantPool();
        int index = zfield.constValueIndex;
        int slotId = zfield.slotId;
        if (index>0){
            switch (zfield.getDescriptor()){
                case "Z":
                case "B":
                case "C":
                case "S":
                case "I":
                    int intValue = (int) runtimeConstantPool.getRuntimeConstant(index).getValue();
                    staticVars.setInt(slotId, intValue);
                    break;
                case "J":
                    long longValue = (long) runtimeConstantPool.getRuntimeConstant(index).getValue();
                    staticVars.setLong(slotId, longValue);
                    break;
                case "F":
                    float floatValue = (float) runtimeConstantPool.getRuntimeConstant(index).getValue();
                    staticVars.setFloat(slotId, floatValue);
                    break;
                case "D":
                    double doubleValue = (double) runtimeConstantPool.getRuntimeConstant(index).getValue();
                    staticVars.setDouble(slotId, doubleValue);
                    break;
                case "Ljava/lang/String;":
                    String stringValue = (String) runtimeConstantPool.getRuntimeConstant(index).getValue();
                    Zobject jStr = StringPool.jString(clazz.getLoader(), stringValue);
                default:
                    break;
            }
        }
    }

    // 解析 将class文件中的符号引用替换为直接引用的过程

}