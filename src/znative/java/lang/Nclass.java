package znative.java.lang;

import runtimedata.Zframe;
import runtimedata.heap.StringPool;
import runtimedata.heap.Zclass;
import runtimedata.heap.ZclassLoader;
import runtimedata.heap.Zobject;
import znative.NativeMethod;

public class Nclass {
    // 获取基本类型的类对象
    public static class getPrimitiveClass implements NativeMethod{
        @Override
        public void run(Zframe frame) {
            Zobject nameObj = frame.getLocalVars().getRef(0);
            String name = StringPool.realString(nameObj);
            ZclassLoader classLoader = frame.getMethod().getClazz().getLoader();
            Zobject jObject = classLoader.loadClass(name).getjObject();
            frame.getOperandStack().pushRef(jObject);
        }
    }

    // 获取类名
    public static class getName0 implements NativeMethod{
        @Override
        public void run(Zframe frame) {
            Zobject self = frame.getLocalVars().getRef(0);
            Zclass clazz = (Zclass) self.extra;
            String name = clazz.getJavaName();
            Zobject nameObj = StringPool.jString(clazz.getLoader,name);
            frame.getOperandStack().pushRef(nameObj);
        }
    }

    //
    public static class desiredAssertionStatus0 implements NativeMethod{
        @Override
        public void run(Zframe frame) {
            frame.getOperandStack().pushBoolean(false);
        }
    }

    public static class isArray implements NativeMethod{
        @Override
        public void run(Zframe frame) {
            Zobject self = frame.getLocalVars().getRef(0);
            Zclass clazz = (Zclass) self.extra;
            frame.getOperandStack().pushBoolean(clazz.isArray());
        }
    }
}
