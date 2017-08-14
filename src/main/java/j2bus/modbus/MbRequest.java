package j2bus.modbus;

import j1base.primitives.Bytes;

/**
 * Created by alexr on 20.01.2017.
 */
public class MbRequest implements Bytes{
    private final byte[] data;

    public MbRequest(Bytes data) {
        this(data.bytes());
    }

    public MbRequest(byte[] data) {
        this.data = data;
    }

    @Override
    public byte[] bytes() {
        return data;
    }
}
