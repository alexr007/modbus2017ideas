package j1base.arr;

import java.util.stream.IntStream;

/**
 * Created by alexr on 22.01.2017.
 *
 * convert bits of integer value
 * to array of values 0-1
 * 1 -> {0,1}
 * 2 -> {1,0}
 * 9 -> {1,0,0,1}
 * maximum array length is 16
 *
 */
public class ArrayFromIntBits {
    private final int values;
    private final int count;

    public ArrayFromIntBits(final int values) {
        this(values, 8);
    }

    public ArrayFromIntBits(final int values, final int count) {
        this.values = values;
        this.count = count;
    }

    public int[] get() {
        return IntStream.range(0, count)
            .map(index -> (values>>index)&0b1)
            .toArray();
    }
}
