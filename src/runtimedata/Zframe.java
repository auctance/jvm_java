// 这里定义栈帧
// 栈顶为栈内的基本元素

// 栈帧 执行方法所需的局部变量表大小和操作数栈深度是编译器提前计算好的
// 储存在class文件中methodinfo结构的code属性中
package runtimedata;

import classfile.attribute.CodeAttribute;
import runtimedata.heap.Zmethod;

public class Zframe {
    // 当前栈帧的前一个栈帧引用
    Zframe lower;
    // 局部变量表
    LocalVars localVars;
    // 操作数栈
    OperandStack operandStack;
    Zthread thread;
    Zmethod method;
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
    // overload
    public Zframe(Zthread thread, Zmethod method){
        this.thread = thread;
        this.method = method;
        localVars = new LocalVars(method.getMaxLocals());
        operandStack = new OperandStack(method.getMaxStack());
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

    public int getNextPC() {
        return nextPc;
    }

    public void setNextPC(int nextPc){
        this.nextPc = nextPc;
    }

    public Zmethod getMethod() {
        return method;
    }

    public void setMethod(Zmethod method) {
        this.method = method;
    }

    public void revertNextPC(){
        this.nextPc = thread.getPc();
    }

}