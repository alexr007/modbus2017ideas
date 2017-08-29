package j2bus.modbus.response;

import java.io.IOException;
import java.util.stream.IntStream;

/**
 * Created by mac on 18.05.2017.
 *
 */
public class WordsFrom2Bytes {
    private final int[] origin;

    public WordsFrom2Bytes(RsAnalyzed analyzed) throws IOException {
        this(analyzed.get());
    }

    public WordsFrom2Bytes(int[] origin) {
        this.origin = origin;
    }

    /**
     * @return words FFFF, ... from sequential bytes (origin) FF,FF, ...
     */
    public int[] get() {
        return IntStream.range(0, origin.length / 2)
            .map(index -> (origin[index*2] & 0xFF) << 8 | origin[index*2+1] & 0xFF)
            .toArray();
    }

    public int get0() {
        return get()[0];
    }
}
