package test;

import instructions.InstructionFactory;
import instructions.base.BytecodeReader;
import sun.jvm.hotspot.interpreter.Bytecode;

// 这里模拟jvm 循环 执行指令的过程
public class interpreter {
    private static void loop(Zthread thread, byte[] byteCode){

        // 取出栈帧
        Zframe frame = thread.popFrame();
        BytecodeReader reader = new BytecodeReader();

        while(true){
            // 取出当前pc位置
            int pc = frame.getNextPc();
            // 设置pc
            thread.setPc(pc);
            reader.reset(byteCode, pc);
            int opCode = reader.readInt8();
            Instruction instruction = InstructionFactory.createInstruction(opCode);
            instruction.fetchOperands(reader);
            frame.setNextPc(reader.getPc());

            System.out.println("pc: %2d, inst: %s \n", pc,instruction.getClass().getSimpleName());
            instruction.execute(frame);

        }
    }

}
