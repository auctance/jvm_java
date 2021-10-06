// 共享区
// 运行时常量池
// 将class文件中的常量池转换为运行时常量池

import classfile.ConstantPool;

public class RuntimeConstantPool {
    public RuntimeConstantPool(Zclass clazz, ConstantPool classFileConstantPool){
        this.clazz = clazz;
        ConstantInfo[] classFileConstantInfos = classFileConstantPool.getInfos();
        int len = classFileConstantInfos.length;
        this.infos = new RuntimeConstantPoo[len];
        for (int i = 1; i<len; i++){
            ConstantInfo classFileConstantInfo = classFileConstantInfos[i];
            switch (classFileConstantInfo.getType()) {
                case ConstantInfo.CONSTANT_Integer:
                    ConstantIntegerInfo intInfo = (ConstantIntegerInfo) classFileConstantInfo;
                    this.infos[i] = new RuntimeConstantInfo<Integer>(intInfo.getVal(), ConstantInfo.CONSTANT_Integer);
                    break;
                case ConstantInfo.CONSTANT_Float:
                    ConstantFloatInfo floatInfo = (ConstantFloatInfo) classFileConstantInfo;
                    this.infos[i] = new RuntimeConstantInfo<Float>(floatInfo.getVal(), ConstantInfo.CONSTANT_Float);
                    break;
                case ConstantInfo.CONSTANT_Long:
                    //Long 和 Double 在转换结束之后，都要进行 i++,以适配 class 文件中常量池的索引
                    ConstantLongInfo longInfo = (ConstantLongInfo) classFileConstantInfo;
                    this.infos[i] = new RuntimeConstantInfo<Long>(longInfo.getVal(), ConstantInfo.CONSTANT_Long);
                    i++;
                    break;
                case ConstantInfo.CONSTANT_Double:
                    ConstantDoubleInfo doubleInfo = (ConstantDoubleInfo) classFileConstantInfo;
                    this.infos[i] = new RuntimeConstantInfo<Double>(doubleInfo.getVal(), ConstantInfo.CONSTANT_Double);
                    i++;
                    break;
                case ConstantInfo.CONSTANT_String:
                    //在对字符串引用进行转换的时候，转为字符串直接引用
                    ConstantStringInfo stringInfo = (ConstantStringInfo) classFileConstantInfo;
                    this.infos[i] = new RuntimeConstantInfo<String>(stringInfo.getString(), ConstantInfo.CONSTANT_String);
                    break;
                case ConstantInfo.CONSTANT_Class:
                    ConstantClassInfo classInfo = (ConstantClassInfo) classFileConstantInfo;
                    //ref 类中真正需要的是 传入上面的 clazz
                    this.infos[i] = new RuntimeConstantInfo<ClassRef>(new ClassRef(this, classInfo), ConstantInfo.CONSTANT_Class);
                    break;
                case ConstantInfo.CONSTANT_Fieldref:
                    ConstantFieldRefInfo fieldRefInfo = (ConstantFieldRefInfo) classFileConstantInfo;
                    this.infos[i] = new RuntimeConstantInfo<FieldRef>(new FieldRef(this, fieldRefInfo), ConstantInfo.CONSTANT_Fieldref);
                    break;
                case ConstantInfo.CONSTANT_Methodref:
                    ConstantMethodRefInfo methodRefInfo = (ConstantMethodRefInfo) classFileConstantInfo;
                    this.infos[i] = new RuntimeConstantInfo<MethodRef>(new MethodRef(this, methodRefInfo), ConstantInfo.CONSTANT_Methodref);
                    break;
                case ConstantInfo.CONSTANT_InterfaceMethodref:
                    ConstantInterfaceMethodRefInfo interfaceMethodRefInfo = (ConstantInterfaceMethodRefInfo) classFileConstantInfo;
                    this.infos[i] = new RuntimeConstantInfo<InterfaceMethodRef>(new InterfaceMethodRef(this, interfaceMethodRefInfo), ConstantInfo.CONSTANT_InterfaceMethodref);
                    break;
                default:
                    //还有一些jdk1.7才开始支持的动态属性,不在本虚拟机的实现范围内
                    break;
            }
        }
    }
}