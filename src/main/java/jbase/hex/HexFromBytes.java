package jbase.hex;

import jbase.primitives.Bytes;
import java.util.stream.IntStream;
import java.util.stream.Collectors;

/**
 * Created by alexr on 18.01.2017.
 * streams 26.07.2017
 */
public class HexFromBytes {
    private final String DELIMITER = ", ";
    private final byte[] origin;

    public HexFromBytes(Bytes origin) {
        this(origin.get());
    }

    public HexFromBytes(byte[] origin) {
        this.origin = origin;
    }

    @Override
    public String toString() {
        return String.format("%d:[%s]", origin.length,
            IntStream
                .range(0, origin.length)
                .mapToObj((int val) -> new HexFromByte(origin[val]).toString())
                .collect(Collectors.joining(DELIMITER))
        );
    }
}
