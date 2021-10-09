// jvm在运行时候 以线程为载体
// 运行时数据区 包括共享的堆、方法区 也包括线程私有的本地方法栈、虚拟机栈以及pc

// 首先实现线程的基本结构

package runtimedata;
import runtimedata.heap.*;

// 定义线程结构体 每个线程中都有一个虚拟机栈的引用
// 虚拟机栈可以是连续的空间 也可以不是
public class Zthread {
    private int pc;
    // 虚拟机栈的引用
    private Zstack stack;
    // 默认栈帧大小为 1024 个
    public Zthread(){stack = new Zstack(1024);}
    // pc 记住指令执行到的位置 所以pc本身是一个int变量
    public int getPc(){return pc;}
    public void setPc(int pc){
        this.pc=pc;
    }
    // 将栈帧压入虚拟机栈
    public void pushFrame(Zframe frame){stack.push(frame);}

    public Zframe popFrame(){return stack.pop();}

    public Zframe getCurrentFrame(){return stack.top();}

    public Zframe createFrame(int maxLocals,int maxStack){
        return new Zframe(this,maxLocals,maxStack);
    }

    public Zframe createFrame(Zmethod method){
        return new Zframe(this,method);
    }

    public boolean isStackEmpty(){
        return stack.size==0;
    }

    public void clearStack(){
        while(!isStackEmpty()){
            stack.pop();
        }
    }

    public Zframe[] getFrames(){
        return stack.getFrames();
    }

}