package znative;

import runtimedata.Zframe;
import znative.NativeMethod;
import znative.java.lang.Nclass;
import znative.java.lang.Nobject;
import znative.java.lang.Nthrowable;

import java.util.HashMap;

//  方法注册中心 native所有方法都需要在方法注册中心注册
public class RegisterCenter {
    private static HashMap<String,NativeMethod> nativeMethods=new HashMap<String, NativeMethod>();
    public static void register(String className, String methodName, String methodDescriptor, NativeMethod nativeMethod){
        String key = className+"~"+methodName+"~"+methodDescriptor;
        nativeMethods.put(key,nativeMethod);
    }
    // 寻找一个本地方法

    public static NativeMethod findNativeMethod(String className, String methodName, String methodDescriptor){
        String key = className+"~"+methodName+"~"+methodDescriptor;
        if (nativeMethods.containsKey(key)){
            return nativeMethods.get(key);
        }
        if ("()V".equals(methodDescriptor)){
            if ("registerNatives".equals(methodName) || "initIds".equals(methodName)){
                return new NativeMethod() {
                    @Override
                    public void run(Zframe frame) {

                    }
                };
            }
        }
        return null;
    }

    // 对外提供jvm启动后的唯一接口 jvm启动后立即调用register方法
    public static void init(){
        register("java/lang/Object","getClass","()Ljava/lang/Class;",new Nobject.getClass());
        register("java/lang/Class", "getPrimitiveClass", "(Ljava/lang/String;)Ljava/lang/Class;", new Nclass.getPrimitiveClass());
        register("java/lang/Class", "getName0", "()Ljava/lang/String;", new Nclass.getName0());
        register("java/lang/Class", "desiredAssertionStatus0", "(Ljava/lang/Class;)Z", new Nclass.desiredAssertionStatus0());
        register("java/lang/Class", "isArray", "()Z", new Nclass.isArray());
        register("java/lang/Throwable", "fillInStackTrace", "(I)Ljava/lang/Throwable;", new Nthrowable.fillInStackTrace());
    }

}
