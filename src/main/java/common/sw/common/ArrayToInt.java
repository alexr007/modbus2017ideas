package common.sw.common;

/**
 * Created by alexr on 22.01.2017.
 *
 * convert array of values 0-1
 * to integer value, where values placed on bits of integer
 * {0,1} -> 1
 * {1,0} -> 2
 * {1,0,0,1} -> 9
 * maximum array length is 16
 *
 */
public class ArrayToInt {
    private final int[] origin;

    public ArrayToInt(int[] origin) {
        this.origin = origin;
    }

    public int get() {
        assert (origin.length<=16);
        int result = 0;
        for (int i = 0; i < origin.length ; i++) {
            result += (origin[i]<<i);
        }
        return result;
    }

    public byte toByte() {
        return (byte)get();
    }
}
