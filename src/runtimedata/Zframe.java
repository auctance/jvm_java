package runtimedata;

public class Zframe {
    Zframe lower;
    LocalVars localVars;
    OperandStack operandStack;
    Zthread thread;
    int nextPC;

    public Zframe (Zthread thread, int maxLocals, int maxStack) {
        this.thread=thread;
        localVars=new LocalVars(maxLocals);
        operandStack=new OperandStack(maxStack);
    }

    public LocalVars getLocalVars(){return localVars;}
    public OperandStack getOperandStack() {
        return operandStack;
    }

    public Zthread getThread() {
        return thread;
    }

    public int getNextPC() {
        return nextPC;
    }

    public void setNextPC(int nextPC) {
        this.nextPC = nextPC;
    }
}
