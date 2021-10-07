package instructions.constants;

import classfile.classconstant.ConstantInfo;
import instructions.base.Index8Instruction;
import runtimedata.OperandStack;
import runtimedata.Zframe;
import runtimedata.heap.*;

// ldc 指令的功能 用来加载基本类型 字符串字面值
// 现在扩展ldc指令
// 根据ldc操作数得到的 运行时常量池中的常量
// 如果是classref类型的 就将其zclass的jobject对象放到操作数栈



public class LDC extends Index8Instruction {
    @Override
    public void execute(Zframe frame) {
        OperandStack operandStack = frame.getOperandStack();
        Zclass clazz = frame.getMethod().getClazz();
        RuntimeConstantInfo runtimeConstant = clazz.getRuntimeConstantPool().getRuntimeConstant(index);
        switch (runtimeConstant.getType()) {
            case ConstantInfo.CONSTANT_Integer:
                operandStack.pushInt((Integer) runtimeConstant.getValue());
                break;
            case ConstantInfo.CONSTANT_Float:
                operandStack.pushFloat((Float) runtimeConstant.getValue());
                break;
            case ConstantInfo.CONSTANT_String:
                Zobject internedStr = StringPool.jString(clazz.getLoader(), (String) runtimeConstant.getValue());
                operandStack.pushRef(internedStr);
                break;
            case ConstantInfo.CONSTANT_Class:
                ClassRef classRef = (ClassRef) runtimeConstant.getValue();
                Zobject jObject = classRef.resolvedClass().getjObject();
                operandStack.pushRef(jObject);
                break;
            // case MethodType, MethodHandle //Java7中的特性，不在本虚拟机范围内
            default:
                break;
        }
    }
}
