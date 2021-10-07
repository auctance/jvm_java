package classfile.attribute;

import Utils.ByteUtils;
import classfile.ClassReader;
import classfile.ConstantPool;
import com.sun.org.apache.bcel.internal.classfile.Code;
import com.sun.org.apache.bcel.internal.classfile.ExceptionTable;
import com.sun.org.apache.bcel.internal.classfile.LineNumber;
import sun.jvm.hotspot.oops.ExceptionTableElement;
import sun.jvm.hotspot.oops.LineNumberTableElement;

//  实例化一种属性信息  attributeInfo
public class CodeAttribute extends AttributeInfo{
    ConstantPool constantPool;
    //  操作数栈 最大深度
    int maxStack;
    // 局部变量表大小
    int maxLocals;
    // 字节码
    byte[] code;
    ExceptionTableEntry[] exceptionTable;
    AttributeInfo[] attributes;

    public CodeAttribute(ConstantPool constantPool){
        this.constantPool = constantPool;
    }

    @Override
    void readInfo(ClassReader reader) {
        maxStack = reader.readUint16();
        maxLocals = reader.readUint16();
        int codeLength = ByteUtils.byteToInt32(reader.readUint32());
        code = reader.readBytes(codeLength);
        exceptionTable = readExceptionTable(reader);
        attributes = readAttributes(reader,constantPool);

    }

    private ExceptionTableEntry[] readExceptionTable(ClassReader reader){
        int exceptionTableLength = reader.readUint16();
        ExceptionTableEntry[] exceptionTable = new ExceptionTableEntry[exceptionTableLength];
        for (int i=0; i< exceptionTableLength;i++){
            exceptionTable[i] = new ExceptionTableEntry(reader);
        }
        return exceptionTable;
    }


    public LineNumberTableAttribute lineNumberTableAttribute(){
        for (int i=0;i <attributes.length;i++){
            if (attributes[i] instanceof LineNumberTableAttribute){
                return (LineNumberTableAttribute) attributes[i];
            }
        }
        return null;
    }

    // 异常表 可能存在异常范围的字节码字段
    public static class ExceptionTableEntry{
        int startPc;
        int endPc;
        int handlePc;
        int catchType;

        public ExceptionTableEntry(ClassReader reader){
            this.startPc=reader.readUint16();
            this.endPc = reader.readUint16();
            this.handlePc = reader.readUint16();
            this.catchType =reader.readUint16();
        }

        public int getStartPc() {
            return startPc;
        }

        public int getEndPc() {
            return endPc;
        }

        public int getHandlePc() {
            return handlePc;
        }

        public int getCatchType() {
            return catchType;
        }
    }

    public int getMaxStack() {
        return maxStack;
    }

    public int getMaxLocals() {
        return maxLocals;
    }

    public byte[] getCode() {
        return code;
    }

    public ExceptionTableEntry[] getExceptionTable() {
        return exceptionTable;
    }
}
