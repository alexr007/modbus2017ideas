package jbus.modbus.command;

import common.sw.primitives.Bytes;

/**
 * Created by alexr on 18.01.2017.
 */
public class MbRead  implements Bytes {

    private final int CMD = 0x03;

    @Override
    public byte[] get() {
        return new byte[] { CMD };
    }
}
