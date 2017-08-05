package jbus.modbus.command;

import jbase.primitives.Bytes;

/**
 * Created by alexr on 18.01.2017.
 */
public class MbDeviceId implements Bytes {
    private final int origin;

    public MbDeviceId(int origin) {
        this.origin = origin;
    }

    @Override
    public byte[] bytes() {
        return new byte[] { (byte) origin};
    }
}
