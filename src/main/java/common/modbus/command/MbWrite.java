package common.modbus.command;

import common.primitives.Bytes;

/**
 * Created by alexr on 18.01.2017.
 */
public class MbWrite  implements Bytes {
    private final int CMD = 0x10;

    @Override
    public byte[] get() {
        return new byte[] { CMD };
    }
}
