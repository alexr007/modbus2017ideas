package jbase.hex;

import jbase.primitives.Bytes;

/**
 * Created by alexr on 18.01.2017.
 */
public class BytesAsHex {
    private final byte[] origin;

    public BytesAsHex(Bytes origin) {
        this(origin.get());
    }

    public BytesAsHex(byte[] origin) {
        this.origin = origin;
    }

    @Override
    public String toString() {
        String prefix = "";
        StringBuilder sb = new StringBuilder();
        sb.append(origin.length);
        sb.append(":[");
        for (byte b : origin) {
            sb.append(prefix);
            prefix = ", ";
            sb.append(new ByteAsHex(b));
        }
        sb.append("]");
        return sb.toString();
    }
}
