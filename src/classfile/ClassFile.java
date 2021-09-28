package classfile;
import Utils.ByteUtils;
// class文件的组成 结构

import classfile.attribute.AttributeInfo;
import com.sun.tools.classfile.SourceFile_attribute;

import javax.sound.midi.Soundbank;

public class ClassFile{

    // 版本号
    private int minorVersion;
    private int majorVersion;
    // 常量池
    public ConstantPool constantPool;
    //
    private int accessFlags;

    // thisclass为一个索引值
    private int thisClass;
    private int superClass;
    //实现的接口在常量池中的索引
    private int[] interfaces;
    // 存放类中的所有字段 包括静态和非静态的 不同的属性通过字段的访问修饰符来读取
    private MemberInfo[] fields;
    private MemberInfo[] methods;
    // 属性表
    private AttributeInfo[] attributes;

    // 构造方法 自动调用读取classreader方法
    public ClassFile(byte[] classData){
        ClassReader reader = new ClassReader(classData);
        read(reader);
    }

    // 读取class文件 解析各个字段
    // 这里提供抽象方法
    private void read(ClassReader reader){
        readAndCheckMagic(reader);
        readAndCheckVersion(reader);
        constantPool = new ConstantPool(reader);
        accessFlags = reader.readUint16();
        thisClass = reader.readUint16();
        superClass = reader.readUint16();
        interfaces = reader.readUint16s();
        // 字段
        fields = MemberInfo.readMembers(reader,constantPool);
        // 方法
        methods = MemberInfo.readMembers(reader,constantPool);
        attributes=AttributeInfo.readAttributes(reader,constantPool);

    }
    // 对比魔数
    private void readAndCheckMagic(ClassReader reader){
        String magic = ByteUtils.bytesToHexString(reader.readUint32());
        if (!magic.equals("CAFEBABE")){
            throw new RuntimeException("java.lang.ClassFormatError:magic");
        }
    }

    // 版本号
    private void readAndCheckVersion(ClassReader reader){
        minorVersion =  reader.readUint16();
        majorVersion = reader.readUint16();
        if (majorVersion==45) return;
        if (minorVersion==0 && majorVersion>=46 && majorVersion<=52) return;
        throw new RuntimeException("java.lang.UnsupportedClassErr!");
    }

    // get方法
    public int getMinorVersion(){return minorVersion;}
    public int getMajorVersion(){return majorVersion;}
    public int getAccessFlags(){return accessFlags;}
    public int getThisClass(){return thisClass;}


    public ConstantPool getConstantPool() {
        return constantPool;
    }

    public int getSuperClass() {
        return superClass;
    }

    public int[] getInterfaces() {
        return interfaces;
    }

    public MemberInfo[] getFields() {
        return fields;
    }

    public MemberInfo[] getMethods() {
        return methods;
    }

    public AttributeInfo[] getAttributes() {
        return attributes;
    }

    public String getClassName(){return constantPool.getClassName(thisClass);}
    public String getSuperClassName(){
        if (superClass>0) return constantPool.getClassName(superClass);
        else return "";
    }

    public String[] getInterfaceNames(){
        String[] interfaceNames=new String[interfaces.length];
        for (int i=0; i<interfaceNames.length; i++){
            interfaceNames[i] = constantPool.getClassName(interfaces[i]);
        }
        return interfaceNames;
    }

    public String getSourceFile(){
        for (AttributeInfo info : attributes){
            if (info instanceof SourceFileAttribute){
                return ((SourceFileAttribute) info).getFileName();
            }
        }
        return "unknow";
    }
}