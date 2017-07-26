package jbase.hex;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by alexr on 18.01.2017.
 * streams 26.07.2017
 */
public class HexFromWords {
    private final String DELIMITER = ", ";
    private final int[] origin;

    public HexFromWords(int[] origin) {
        this.origin = origin;
    }

    @Override
    public String toString() {
        return String.format("%d:[%s]", origin.length,
            IntStream
                .range(0, origin.length)
                .mapToObj((int val) -> new HexFromWord(origin[val]).toString())
                .collect(Collectors.joining(DELIMITER))
        );
    }
}
