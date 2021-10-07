package classfile.attribute;
// 定义class文件中各种属性的集合

// 也就是属性的容器 属性的抽象

import Utils.ByteUtils;
import classfile.ClassReader;
import classfile.ConstantPool;

import javax.smartcardio.ATR;

// 定义一个抽象类 后序实例化属性信息
public abstract class AttributeInfo{
    // 定义读取属性信息的抽象方法
    abstract void readInfo(ClassReader reader);

    // 读取单个的属性
    private static AttributeInfo readAttribute(ClassReader reader, ConstantPool constantPool){
        // 确定单个属性的位置 属性的长度
        int attrNameIndex = reader.readUint16();
        String attrName = constantPool.getUtf8(attrNameIndex);
        int attrLen = ByteUtils.byteToInt32(reader.readUint32());
        AttributeInfo attrInfo = create(attrName,attrLen,constantPool);
        attrInfo.readInfo(reader);
        return attrInfo;
    }

    // 读取属性表
    public static AttributeInfo[] readAttributes(ClassReader reader,ConstantPool constantPool){
        int attributesCount = reader.readUint16();
        AttributeInfo[] attributes = new AttributeInfo[attributesCount];
        for (int i=0; i<attributesCount; i++){
            attributes[i] = readAttribute(reader,constantPool);

        }
        return attributes;
    }

    // 解析属性 jvm中规范预定义了23种
    // 读取单个属性的时候调用该方法
    private static AttributeInfo create(String attrName, int attrLen, ConstantPool constantPool){
        if (attrName.equals("Code")) {
            return new CodeAttribute(constantPool);
        }else if (attrName.equals("ConstantValue")){
            return new ConstantValueAttribute();
        }else if (attrName.equals("Deprecated")){
            return new DeprecatedAttribute();
        }else if (attrName.equals("Exceptions")){
            return new ExceptionsAttribute();
        }else if (attrName.equals("LineNumberTable")){
            return new LineNumberTableAttribute();
        }else if (attrName.equals("LocalVariableTable")){
            return new LocalVariableTableAttribute();
        }else if (attrName.equals("SourceFile")){
            return new SourceFileAttribute(constantPool);
        }else if (attrName.equals("Synthetic")){
            return new SyntheticAttribute();
        } else {
            return new UnparsedAttribute(attrName, attrLen);
        }
    }

}