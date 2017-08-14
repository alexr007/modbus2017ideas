package j1base.hex;

import j1base.IterableToString;
import java.util.stream.IntStream;

/**
 * Created by alexr on 18.01.2017.
 * streams 26.07.2017
 */
public class HexFromWords {
    private final String DELIMITER = ", ";
    private final int[] origin;

    public HexFromWords(short[] origin) {
        this(IntStream
            .range(0, origin.length)
            .map(i -> origin[i])
            .toArray());
    }

    public HexFromWords(int[] origin) {
        this.origin = origin;
    }

    @Override
    public String toString() {
        return
            new IterableToString<Integer>(origin, val -> new HexFromWord(val).toString())
                .toString();
    }
}
