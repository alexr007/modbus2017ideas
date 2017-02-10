package common.hw.modbus.command;

import common.sw.primitives.Bytes;

/**
 * Created by alexr on 18.01.2017.
 */
public class MbLength implements Bytes {

    private final int length;

    public MbLength(Bytes origin) {
        this(origin.get());
    }

    public MbLength(byte[] origin) {
        this.length = origin.length;
    }

    @Override
    public byte[] get() {
        return new byte[] { (byte) length };
    }
}
