package jbase.hex;

import jbase.primitives.Bytes;

import java.util.Arrays;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by alexr on 18.01.2017.
 */
public class HexFromBytes {
    private final byte[] origin;

    public HexFromBytes(Bytes origin) {
        this(origin.get());
    }

    public HexFromBytes(byte[] origin) {
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
            sb.append(new HexFromByte(b));
        }
        sb.append("]");
        return sb.toString();
    }
}
