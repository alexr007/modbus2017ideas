package j2bus.modbus.command;

import j1base.primitives.Bytes;

/**
 * Created by alexr on 18.01.2017.
 */
public class MbWrite  implements Bytes {
    private final int CMD = 0x10;

    @Override
    public byte[] bytes() {
        return new byte[] { CMD };
    }
}
