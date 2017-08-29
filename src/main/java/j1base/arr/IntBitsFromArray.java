package j1base.arr;

import java.util.stream.IntStream;

/**
 * Created by alexr on 22.01.2017.
 *
 * convert array of values
 * array items must be only 0-1
 * to integer value, where values placed on bits of integer
 * {0,1} -> 1
 * {1,0} -> 2
 * {1,0,0,1} -> 9
 * maximum array length is 16
 *
 */
public class IntBitsFromArray {
    private final int[] origin;

    public IntBitsFromArray(int[] origin) {
        this.origin = origin;
    }

    public int get() {
        if (origin.length>16) {
            throw new IllegalArgumentException(String.format("IntBitsFromArray. length mustme <=16 found:%d",origin.length));
        }
        return IntStream.range(0, origin.length)
            .map(index -> origin[index]<<index)
            .sum();
    }

    public byte toByte() {
        return (byte)get();
    }
}
