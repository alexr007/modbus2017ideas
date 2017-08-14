package j2bus.modbus.command;

import j1base.primitives.Bytes;

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
