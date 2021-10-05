// jvm在运行时候 以线程为载体
// 运行时数据区 包括共享的堆、方法区 也包括线程私有的本地方法栈、虚拟机栈以及pc

// 首先实现线程的基本结构

import runtimedata.Zframe;
import runtimedata.Zstack;

public class Zthread {
    int pc;
    // 虚拟机栈的引用
    Zstack zstack;
    // 默认栈帧大小为 1024 个
    public Zthread(){stack = new Zstack(1024);}
    // pc 记住指令执行到的位置 所以pc本身是一个int变量
    public int getPc(){return pc;}
    public void setPc(int pc){
        this.pc=pc;
    }
    // 将栈帧压入虚拟机栈
    public void pushFrame(Zframe frame){zstack.push(frame);}

    public Zframe popFrame(){return stack.pop();}

    public Zframe getCurrentFrame(){return stack.top();}

    public Zframe createFrame(int maxLocals,int maxStack){
        return new Zframe(this,maxLocals,maxStack);
    }
}