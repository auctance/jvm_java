// 很多指令的操作数是类似的

// 这里定义一些抽象的类 实现instruction接口 实现 取数 方法
// 由具体的类去实例化这些抽象类 实现execute方法


import instructions.base.BytecodeReader;

public abstract class NoOperandsInstruction implements Instruction{
    @Override
    public void fetchOperands(BytecodeReader reader){

    }
}