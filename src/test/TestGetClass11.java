package test;

import classpath.ClassPath;
import Utils.Cmd;

import java.util.Scanner;

import instructions.InstructionFactory;
import instructions.base.BytecodeReader;
import runtimedata.heap.StringPool;
import znative.RegisterCenter

// 测试本地方法调用
public class TestGetClass11 {
    public static void main(String[] args){
        System.out.println("the same as test!");;
        Scanner in = new Scanner(System.in);
        String cmdLine = in.nextLine();
        Cmd cmd = new Cmd(cmdLine);
        RegisterCenter.init();
        ClassPath classPath = new ClassPath(cmd.getXJreOption(), cmd.getCpOption());
        ZclassLoader classLoader = new ZclassLoader(classPath);
        Zclass testClass = classLoader.loadClass(cmd.getClazz());
        Zmethod testMethod = testClass.getMethod("test","()V");

        // 初始化栈帧
        Zthread thread = new Zthread();
        Zframe frame = thread.createFrame(testMethod);
        Zframe outFrame = frame;
        thread.pushFrame(frame);
        BytecodeReader reader = new BytecodeReader();

        while (true){
            frame = thread.getCurrentFrame();
            int pc = frame.getNextPC();
            thread.setPc(pc);

            // decode
            reader.reset(frame.getMethod().getCode(),pc);
            int opCode = reader.readUint8();
            try {
                Instruction instruction = InstructionFactory.createInstruction(opCode);
                instruction.fetchOprands(reader);
                frame.setNextPC(reader.getPc());
                instruction.execute(frame);
                if (frame==outFrame){
                    System.out.println("current instruction:"+pc+instruction.getClass().getSimpleName());
                }
                if (thread.isStackEmpty()){
                    if (!frame.getOperandStack().isEmpty()){
                        System.out.println("return:"+  frame.getOperandStack()+popInt());;

                    }
                    break;
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        Zobject ref = outFrame.getLocalVars().getRef(0);
        System.out.println(StringPool.realString(ref));

    }
    public static void test(){
        String s1 = int[].class.getName();
    }
}
