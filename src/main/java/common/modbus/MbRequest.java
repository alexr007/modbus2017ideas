package common.modbus;

import common.primitives.Bytes;

/**
 * Created by alexr on 20.01.2017.
 */
public class MbRequest implements Bytes{
    private final byte[] data;

    public MbRequest(Bytes data) {
        this(data.get());
    }

    public MbRequest(byte[] data) {
        this.data = data;
    }

    @Override
    public byte[] get() {
        return data;
    }
}
