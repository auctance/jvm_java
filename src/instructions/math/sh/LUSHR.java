package instructions.math.sh;

import instructions.base.NoOperandsInstruction;
import runtimedata.OperandStack;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc: 逻辑右移,或叫无符号右移运算符“>>>“只对位进行操作，没有算术含义，它用0填充左侧的空位。
 */
public class LUSHR extends NoOperandsInstruction {
    @Override
    public void execute(Zframe frame) {
        OperandStack stack = frame.getOperandStack();
        int val2 = stack.popInt();  //要移动多少bit
        long val1 = stack.popLong();  //要进行位移操作的变量
        int s = val2 & 0x3f; //int变量只有32位，所以只取val2的后5个比特就足够表示位移位数了,位移操作符右侧必须是无符号整数，所以需要对val2进行类型转换

        //但是Java中对于大数左移超出后,也会变成负数,所以这里不做额外处理了
        long res = val1 >>> s;

        stack.pushLong(res);
    }
}
