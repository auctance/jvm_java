package classfile;

import classfile.classconstant.*;

public class ConstantPool {
    // 常量池的抽象 保存所有常量
    ConstantInfo[] infos;
    public ConstantInfo[] getInfos(){return infos;}
    // 常量数量
    private int constantPoolCount;
    private int realConstantPoolCount;

    public ConstantPool(ClassReader reader){
        constantPoolCount=reader.readUint16();
        infos = new ConstantInfo[constantPoolCount];
        for (int i=1;i<constantPoolCount;i++){
            infos[i]=ConstantInfo.readConstantInfo(reader,this);
            realConstantPoolCount++;
            if((infos[i] instanceof ConstantLongInfo) || (infos[i] instanceof ConstantDoubleInfo)) i++;

        }
    }

    private ConstantInfo getConstantInfo(int index){
        if(index>0 && index<constantPoolCount){
            ConstantInfo info = infos[index];
            if (info!=null) return info;
        }
        throw new NullPointerException("invalid constant pool index");
    }

    //常量池查找字段或方法的名字和描述符
    public String getName(int index) {
        ConstantNameAndTypeInfo info = (ConstantNameAndTypeInfo) getConstantInfo(index);
        return getUtf8(info.nameIndex);
    }

    //常量池查找字段或方法的描述符,描述符其实就是由其对应的类型名字对应而成;
    public String getType(int index) {
        ConstantNameAndTypeInfo info = (ConstantNameAndTypeInfo) getConstantInfo(index);
        return getUtf8(info.descriptorIndex);
    }

    public String[] getNameAndType(int index) {
        String[] str = new String[2];
        ConstantNameAndTypeInfo info = (ConstantNameAndTypeInfo) getConstantInfo(index);
        str[0] = getUtf8(info.nameIndex);
        str[1] = getUtf8(info.descriptorIndex);
        return str;
    }

    public String getClassName(int index) {
        ConstantClassInfo info = (ConstantClassInfo) getConstantInfo(index);
        return getUtf8(info.nameIndex);
    }

    //只要调用这个方法，一定是想去读字符串常量了，所以拿到index所对应的常量后，直接强转为ConstantUtf8Info，然后获取其val值；
    public String getUtf8(int index) {
        return ((ConstantUtf8Info) getConstantInfo(index)).val;
    }

    //测试方法，正式版本祛除
    public int getConstantPoolCount() {
        return realConstantPoolCount;
    }


}
