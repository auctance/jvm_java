// 利用slot容器 存储局部变量（虚拟机栈的栈帧包括局部变量以及操作数）

// 局部变量储存 一个栈帧中的方法（函数）运行时候的变量数据
package runtimedata;
public class LocalVars extends Slots{
    public LocalVars(int size){
        super(size);
    }
}