package instructions.loads.loadxarr;

import instructions.base.NoOperandsInstruction;
import instructions.loads.Load;
import runtimedata.OperandStack;
import runtimedata.Zframe;
import runtimedata.heap.Zobject;

/**
 * @author zachaxy
 * @date 17/12/29
 * 获取数组指定索引值：eg char c = chars[2];
 */
public class CALOAD extends NoOperandsInstruction {
    @Override
    public void execute(Zframe frame) {
        OperandStack operandStack = frame.getOperandStack();
        //数组元素的索引值
        int index = operandStack.popInt();
        //数组对象的引用
        Zobject arrRef = operandStack.popRef();

        Load.checkNotNull(arrRef);
        //得到数组对象
        char[] refs = arrRef.getChars();
        Load.checkIndex(arrRef.getArrayLen(), index);
        //将数组的 index 的值压栈
        operandStack.pushInt(refs[index]);
    }
}
