package common.primitives;

import common.IntAsHex;
import common.modbus.command.MbWrap;

public class Byte {

    private final int origin;

    public Byte(int origin) {
        this.origin = 0xFF & origin;
    }

    public int get() {
        return origin;
    }

    @Override
    public String toString() {
        return new IntAsHex(origin).toString();
    }

    public Bytes toBytes() {
        return
            new MbWrap(origin);
    }

}
