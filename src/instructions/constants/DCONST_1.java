package instructions.constants;

import instructions.base.NoOperandsInstruction;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class DCONST_1 extends NoOperandsInstruction {
    @Override
    public void execute(Zframe frame) {
        frame.getOperandStack().pushDouble(1.0);
    }
}
