// 虚拟机栈的基本结构 以及一些基本方法

import runtimedata.Zframe;

import java.util.EmptyStackException;

public class Zstack {
    int maxSize;
    // 当前栈帧数量
    int size;
    // 栈顶的帧
    private Zframe _top;
    // 构造方法 调用时候自动生成一个虚拟机栈
    private Zstack(int maxSize){
        this.maxSize=maxSize;
    }

    // 新添加一个栈帧
    void push(Zframe zframe){
        if (size>maxSize){
            throw new StackOverflowError("no space in stack!");

        }
        if (_top!=null){
            frame.lower = _top;
        }
        _top = frame;
        size++;
    }

    // 弹出
    Zframe pop(){
        if (_top==null){
            throw new EmptyStackException();
        }
        Zframe tmp = _top;
        _top = tmp.lower;
        tmp.lower = null;
        size--;
        return tmp;
    }

    // 查看栈顶元素
    Zframe top(){
        if (_top==null) {
            throw new EmptyStackException();
        }
        return  _top;
    }
}