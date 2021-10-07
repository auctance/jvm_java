package classfile;

import classfile.attribute.AttributeInfo;
import classfile.attribute.CodeAttribute;
import classfile.attribute.ConstantValueAttribute;
import classfile.attribute.ExceptionsAttribute;

public class MemberInfo {
    ConstantPool constantPool;
    int accessFlags;
    int nameIndex;
    int descriptorIndex;
    AttributeInfo[] attributes;
    public MemberInfo(ClassReader reader, ConstantPool constantPool){
        this.constantPool = constantPool;
        accessFlags = reader.readUint16();
        nameIndex = reader.readUint16();
        descriptorIndex = reader.readUint16();
        attributes = AttributeInfo.readAttributes(reader,constantPool);
    }
    public static MemberInfo[] readMembers(ClassReader reader, ConstantPool constantPool){
        int memberCount = reader.readUint16();
        MemberInfo[] members = new MemberInfo[memberCount];
        for (int i =0;i <memberCount; i++){
            members[i] = new MemberInfo(reader, constantPool);
        }
        return members;
    }

    public int getAccessFlags(){
        return accessFlags;
    }
    public String getName(){
        return constantPool.getUtf8(nameIndex);
    }
    public String getDescriptor(){
        return constantPool.getUtf8(descriptorIndex);
    }

    public CodeAttribute getCodeAttribute(){
        for(AttributeInfo info : attributes){
            if (info instanceof CodeAttribute){
                return (CodeAttribute) info;
            }
        }
        return null;
    }

    public ExceptionsAttribute getExceptionsAttribute(){
        for(int i=0;i<attributes.length;i++){
            if (attributes[i] instanceof ExceptionsAttribute){
                return (ExceptionsAttribute) attributes[i];
            }
        }
        return null;
    }

    public ConstantValueAttribute getConstantValueAttribute(){
        for(AttributeInfo info : attributes){
            if (info instanceof ConstantValueAttribute){
                return (ConstantValueAttribute) info;
            }
        }
        return null;
    }
}
