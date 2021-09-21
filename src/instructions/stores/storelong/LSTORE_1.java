package instructions.stores.storelong;

import instructions.base.NoOperandsInstruction;
import instructions.stores.Store;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class LSTORE_1 extends NoOperandsInstruction {
    @Override
    public void execute(Zframe frame) {
        Store.lstore(frame,1);
    }
}
