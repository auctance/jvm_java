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


import classpath.ClassPath;
import com.sun.xml.internal.ws.api.ha.StickyFeature;
import runtimedata.heap.ClassFile;
import sun.jvm.hotspot.oops.AccessFlags;

import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipFile;

public class ZclassLoader {
    ClassPath classPath;
    // 缓存加载过的类
    HashMap<String, Zclass> map;
    public ZclassLoader(ClassPath classPath){
        // 初始化两个变量
        this.classPath = classPath;
        this.map = new HashMap<String, Zclass>();

        loadBasicClasses();
        loadPrimitiveClasses();
    }

    private void loadBasicClasses(){
        Zclass jlClassClass = loadClass("java/lang/Class");
        for (Map.Entry<String, Zclass> entry:map.entrySet()){
            Zclass jClass = entry.getValue();
            if (jClass.jObject == null){
                jClass.jObject = jlClassClass.newObject();
                jClass.jObject.extra = jClass;
            }
        }
    }

    // 加载基本类型的类 void boolean byte
    private void loadPrimitiveClasses(){
        for(Map.Entry<String,String> entry:ClassNameHelper.primitiveTypes.entrySet()){
            String className = entry.getKey();
            loadPrimitiveClass(className);
        }
    }

    //  加载基本类型
    private void loadPrimitiveClass(String className){
        Zclass clazz = new Zclass(AccessFlag.ACC_PUBLIC,className,this,true,null,new Zclass[]{});
        clazz.jObject = map.get("java/lang/Class").newObject();
        clazz.jObject.extra = clazz;
        map.put(className,clazz);
    }

    public static HashMap<String, String> primitiveTypes;

    static {
        primitiveTypes = new HashMap<String , String >();
        primitiveTypes.put("void","V");
        primitiveTypes.put("boolean", "Z");
        primitiveTypes.put("byte", "B");
        primitiveTypes.put("short", "S");
        primitiveTypes.put("int", "I");
        primitiveTypes.put("long", "J");
        primitiveTypes.put("char", "C");
        primitiveTypes.put("float", "F");
        primitiveTypes.put("double", "D");
    }

    public Zclass loadClass(String name){
        // 如果缓存中有zclass 直接加载
        if (map.containsKey(name)){
            return map.get(name);
        }
        Zclass clazz;
        // 如果是zclass数组
        if (name.charAt(0)=='['){
            clazz = loadArrayClass(name);
        }else{
            clazz = loadNonArrayClass(name);
        }

        // 为每一个class都关联一个元类
        Zclass jlClassClass = map.get("java/lang/Class");
        if (jlClassClass !=null){
            clazz.jObject = jlClassClass.newObject();
            clazz.jObject.extra = clazz;

        }
        return clazz;
    }

    private Zclass loadArrayClass(String name){
        Zclass clazz = new Zclass(AccessFlags.ACC_PUBLIC,name,this,true,
                loadClass("java/lang/Object"),
                new Zclass[]{loadClass("java/lang/Cloneable"),
                        loadClass("java/io/Serializable")
                }
                );
        map.put(name,clazz);
        return clazz;
    }

    private Zclass loadNonArrayClass(String name){
        byte[] data = readClass(name);
        Zclass clazz = defineClass(data);
        link(clazz);
        return clazz;
    }



// 类的全限定名：将。替换为/ 作为唯一确定的位置的一种写法
// 加载阶段虚拟机需要做两件事情
// 1。 通过类的全限定名来获取类的二进制字节流 寻找类所在路径并读取class文件 映射为classFile对象
// 2。 将字节流代表的静态储存结构转孩奴为运行时动态储存结构 将classfile转换为zclass

    // 读取class文件 并且将字节码转换为zclass
    private byte[] readClass(String name){
        byte[] data = classPath.readClass(name);
        if (data!=null){
            return data;
        }else{
            throw new ClassCastException("class name:"+name);
        }
    }

    //
    private Zclass defineClass(byte[] data){
        Zclass clazz = parseClass(data);
        clazz.loader = this;
        resolveSuperClass(clazz);
        resolveInterfaces(clazz);
        map.put(clazz.thisClassName,clazz);
        return clazz;
    }


    private Zclass parseClass(byte[] data){
        ClassFile cf = new ClassFile(data);
        return new Zclass(cf);
    }

    // 加载当前类的父类
    private void resolveSuperClass(Zclass clazz){
        if(!"java/lang/Object".equals(clazz.thisClassName)){
            clazz.superClass = clazz.loader.loadClass(clazz.superClassNmme);
        }
    }

    // 加载当前类的接口类
    private void resolveInterfaces(Zclass clazz){
        int count =  clazz.interfaceNames.length;
        clazz.interfaces = new Zclass[count];
        for (int i=0; i<count; i++){
            clazz.interfaces[i] = clazz.loader.loadClass(clazz.interfacesNames[i]);
        }
    }

    //
    private void link(Zclass clazz){
        verify(clazz);
        prepare(clazz);
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