package runtimedata;

import java.util.EmptyStackException;

public class Zstack {
    int maxSize;
    int size;
    private Zframe _top;

    public Zstack(int maxSize){this.maxSize=maxSize;}

    void push(Zframe frame){
        if(size>maxSize) throw new StackOverflowError();

        if(_top!=null) frame.lower=_top;

        _top=frame;
        size++;
    }

    Zframe pop(){
        if(_top==null) throw new EmptyStackException();
        Zframe tmp = _top;
        _top=tmp.lower;
        tmp.lower=null;
        size--;
        return tmp;
    }

    Zframe top(){
        if(_top==null) throw new EmptyStackException();
        return _top;
    }
}
