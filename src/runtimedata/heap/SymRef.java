package runtimedata.heap;

import sun.jvm.hotspot.debugger.cdbg.Sym;

// 类加载器---类引用的解析
public class SymRef {
    // 符号引用所在的运行时常量池指针
    RuntimeConstantPool runtimeConstantPool;
    // 类的全限定名
    String className;
    // 符号引用的真正类
    Zclass clazz;
    public SymRef(RuntimeConstantPool runtimeConstantPool){
        this.runtimeConstantPool = runtimeConstantPool;
    }

    // 类引用转直接引用
    public Zclass resolvedClass() {
        if (clazz==null){
            resolvedClassRef();

        }
        return clazz;
    }

    private void resolvedClassRef(){
        Zclass d = runtimeConstantPool.clazz;
        Zclass c = d.loader.loadClass(className);
        if (!c.isAccessibleTo(d)){
            throw new IllegalAccessError(d.thisClassName+"can not access"+c.thisClassName);
        }
        clazz = c;
    }
}
