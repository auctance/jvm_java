package znative.java.lang;

import runtimedata.Zframe;
import runtimedata.heap.Zobject;
import znative.NativeMethod;

public class Nobject {
    public static class getClass implements NativeMethod{
        @Override
        public void run(Zframe frame) {
            // 从局部变量表中获取 非静态方法的实际的第一个参数 -- this
            Zobject self = frame.getLocalVars().getRef(0);
            Zobject jObject = self.getClazz().getjObect();
            frame.getOperandStack().pushRef(jObject);
        }
    }
}
