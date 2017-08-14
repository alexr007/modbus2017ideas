package j1base.hex;

import j1base.IterableToString;
import j1base.primitives.Bytes;

/**
 * Created by alexr on 18.01.2017.
 * streams 26.07.2017
 */
public class HexFromBytes {
    private final byte[] origin;

    public HexFromBytes(Bytes origin) {
        this(origin.bytes());
    }

    public HexFromBytes(byte[] origin) {
        this.origin = origin;
    }

    @Override
    public String toString() {
        return
            new IterableToString<Integer>(origin, val -> new HexFromByte(val).toString())
                .toString();
    }
}
