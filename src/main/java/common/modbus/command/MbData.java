package common.modbus.command;

import common.primitives.Bytes;
import common.primitives.Word;

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
        this(origin.get());
    }

    public MbData(byte[] origin) {
        this.origin = origin.clone();
    }

    @Override
    public byte[] get() {
        return origin;
    }
}
