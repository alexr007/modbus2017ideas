package j2bus.modbus.command;

import j1base.primitives.Byte;
import j1base.primitives.Bytes;

/**
 * Created by alexr on 21.01.2017.
 *
 * wrap single byte to Bytes interface
 *
 */
public class MbWrap implements Bytes {
    private final int origin;

    public MbWrap(Byte origin) {
        this(origin.get());
    }

    public MbWrap(int origin) {
        this.origin = origin;
    }

    @Override
    public byte[] bytes() {
        return new byte[]{(byte) origin};
    }
}
