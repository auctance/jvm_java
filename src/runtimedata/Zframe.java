// 这里定义栈帧
// 栈顶为栈内的基本元素

import runtimedata.LocalVars;
import runtimedata.OperandStack;

public class Zframe {
    // 当前栈帧的前一个栈帧引用
    Zframe lower;
    // 局部变量表
    LocalVars localVars;
    // 操作数栈
    OperandStack operandStack;
    Zthread zthread;
    // pc
    int nextPc;

    // 构造方法 作为栈帧 获取当前线程的相关变量
    public Zframe(Zthread thread, int maxLocals, int maxStack){
        // 该栈帧的线程为 目前的zthread
        this.thread = thread;
        // 声明最大空间的局部变量 以及 操作数栈
        localVars = new LocalVars(maxLocals);
        operandStack = new OperandStack(maxStack);
    }

    // get set方法

    public LocalVars getLocalVars() {
        return localVars;
    }

    public OperandStack getOperandStack() {
        return operandStack;
    }

    public Zthread getThread() {
        return thread;
    }

    public int getNextPc() {
        return nextPc;
    }

    public void setNextPc(int nextPc){
        this.nextPc = nextPc;
    }
}