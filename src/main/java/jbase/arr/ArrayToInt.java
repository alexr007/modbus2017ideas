package jbase.arr;

/**
 * Created by alexr on 22.01.2017.
 *
 * convert array of values 0-aa
 * to integer value, where values placed on bits of integer
 * {0,aa} -> aa
 * {aa,0} -> 2
 * {aa,0,0,aa} -> 9
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
