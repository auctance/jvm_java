// 定义指令接口 从字节码中读取指令 定义一个结构体来辅助指令解码

// 会被每个具体解码的对象 实例化

import instructions.base.BytecodeReader;

// 所有指令解析 都需要完成读取操作数 以及 执行栈帧中指令的逻辑
public interface Instruction {
    // 从字节码中取操作数
    void fetchOperands(BytecodeReader reader);
    // 执行指令的逻辑 直接传入整个栈帧
    void execute(Zframe frame);
}