package jbase;

/**
 * Created by alexr on 22.01.2017.
 */
public class IterableToString<T> {
    private final T[] origin;
    private String S_OPENER = ":[";
    private String S_CLOSER = "]";
    private String S_DELIM = ", ";

    public IterableToString(T[] origin) {
        this.origin = origin;
    }

    @Override
    public String toString() {
        String prefix = "";
        StringBuilder sb = new StringBuilder();
        sb.append(origin.length);
        sb.append(S_OPENER);
        for (T val : origin) {
            sb.append(prefix);
            prefix = S_DELIM;
            sb.append(val);
        }
        sb.append(S_CLOSER);
        return sb.toString();
    }
}
