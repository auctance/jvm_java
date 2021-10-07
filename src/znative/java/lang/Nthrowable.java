package znative.java.lang;

import runtimedata.Zobject;
import znative.NativeMethod;

import javax.naming.Name;

public class Nthrowable {
    public static class fillInStackTrace implements NativeMethod{
        @Override
        public void run(Zframe frame) {
            Zobject self = frame.getLocalVars().getRef(0);
            frame.getOperandStack().pushRef(self);
            self.extra = createStackTraceElements(self,frame.getThread());
        }

        private NStackTraceElement[] createStackTraceElements(Zobject exObj, Zthread thread){
            int skip = distanceToObject(exObj.getClazz())+2;

            Zframe[] frames = thread.getFrames();
            NStackTraceElement[] stes = new NStackTraceElement[frames.length-skip];
            for (int i=skip; i<frames.length;i++){
                stes[i-skip] = createStackTraceElements(frames[i]);
            }
            return stes;

        }

        private int distanceToObject(Zclass exClazz){
            int distance=0;
            for(Zclass c=exClazz.getSuperClass();c!=null;c=c.getSuperClass()){
                distance++;
            }
            return distance;
        }

        // 拿到每一帧的消息
        private NStackTraceElement createStackTraceElement(Zframe frame){
            Zmethod method = frame.getMethod();
            Zclass clazz = method.getClazz();
            return new NStackTraceElement(clazz.getSourceFile(),clazz.getJavaName(),method.getName(),method.getLineNumber(frame.getNextPC()-1));

        }
    }
}
