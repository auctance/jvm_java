
// 运行时常量池
// 运行时候将常量池的数据加载到运行时常量池

import classfile.ClassReader;
import classfile.ConstantPool;

public class RuntimeConstantPool {
    // 保存常量池中所有常量信息 也就是常量池的抽象
    ConstantInfo[] infos;
    // class文件常量池中常量的数量
    int constantPoolCount;
    // 调用常量池的read方法
    public ConstantPool(ClassReader reader){
        constantPoolCount = reader.readUint16();
        infos  = new ConstantInfo[constantPoolCount];
        // 将常量读到constantinfo数组中去
        // contactinfo的read方法是需要传入constant pool的reader对象
        for (int i=1;i <constantPoolCount; i++){
            infos[i] = ConstantInfo.readConstantInfo(raader,this);
            if ((infos[i] instanceof ConstantLongInfo) || (infos[i] instanceof ConstantDoubleInfo) ){
                i++;

            }
        }
    }

}