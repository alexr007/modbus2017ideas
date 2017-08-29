package j1base.primitives;

import j1base.hex.HexFromByte;
import j2bus.modbus.command.MbWrap;

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
        return new HexFromByte(origin).toString();
    }

}
