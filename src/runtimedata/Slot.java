// 局部变量表是一个数组  每个数组元素可能是int或者引用或者double
// 数组元素 一个元素可以存放一个int类型或者引用

// 两个连续的元素可以用来存放一个long或者double类型
package runtimedata;

public class Slot {
    public int num;
    public Zobject ref;
    public Slot(){}
}