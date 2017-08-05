package jbus.modbus.command;

import jbase.primitives.Bytes;

/**
 * Created by alexr on 18.01.2017.
 */
public class MbLength implements Bytes {

    private final int length;

    public MbLength(Bytes origin) {
        this(origin.bytes());
    }

    public MbLength(byte[] origin) {
        this.length = origin.length;
    }

    @Override
    public byte[] bytes() {
        return new byte[] { (byte) length };
    }
}
