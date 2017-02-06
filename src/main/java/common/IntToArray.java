package common;

/**
 * Created by alexr on 22.01.2017.
 */
public class IntToArray {
    private final int values;
    private final int count;

    public IntToArray(int values) {
        this(values, 8);
    }

    public IntToArray(int values, int count) {
        this.values = values;
        this.count = count;
    }

    public int[] get() {
        int[] result = new int[count];
        for (int i = 0; i < count; i++) {
            result[i]=(values>>i)&0b1;
        }
        return result;
    }
}
