package j1base.primitives;

import j1base.hex.HexFromWord;
import j2bus.modbus.command.MbMerged;
import j2bus.modbus.command.MbWrap;

public class Word {

    private final int origin;

    public Word(short origin) {
        this(0xFFFF & origin);
    }

    public Word(int origin) {
        this.origin = 0xFFFF & origin;
    }

    public Byte lo() {
        return new Byte(0x00FF & origin);
    }

    public Byte hi() {
        return new Byte(0x00FF & (origin >> 8));
    }

    public int get() {
        return origin;
    }

    @Override
    public String toString() {
        return new HexFromWord(origin).toString();
    }

    public Bytes toBytes() {
        return new MbMerged(
            new MbWrap(hi()),
            new MbWrap(lo())
        );
    }
}
