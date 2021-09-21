package runtimedata;

public class Zthread {
    int pc;
    Zstack stack;
    public Zthread(){stack=new Zstack(1024);}
    public int getPc(){ return pc;}
    public void setPc(int pc){this.pc=pc;}
    public void pushFrame(Zframe frame){stack.push(frame);}
    public Zframe popFrame(){stack.pop();}
    public Zframe getCurrentFrame(){return stack.pop();}
    public Zframe createFrame(int maxLocals,int maxStack){
        return new Zframe(this,maxLocals,maxStack);
    }
}
