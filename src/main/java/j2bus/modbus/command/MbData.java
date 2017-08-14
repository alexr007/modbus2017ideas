package j2bus.modbus.command;

import j1base.primitives.Bytes;
import j1base.primitives.Word;

/**
 * Created by alexr on 18.01.2017.
 *
 * wrap byte[] to Bytes interface
 *
 */
public class MbData implements Bytes {
    private final byte[] origin;

    public MbData(Word origin) {
        this(origin.toBytes());
    }

    public MbData(Bytes origin) {
        this(origin.bytes());
    }

    public MbData(byte[] origin) {
        this.origin = origin.clone();
    }

    @Override
    public byte[] bytes() {
        return origin;
    }
}
