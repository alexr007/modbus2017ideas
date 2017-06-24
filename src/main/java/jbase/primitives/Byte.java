package jbase.primitives;

import jbase.hex.HexFromByte;
import jbus.modbus.command.MbWrap;

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

    public Bytes toBytes() {
        return
            new MbWrap(origin);
    }

}
