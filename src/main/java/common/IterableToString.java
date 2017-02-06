package common;

/**
 * Created by alexr on 22.01.2017.
 */
public class IterableToString<T> {
    private final T[] origin;

    public IterableToString(T[] origin) {
        this.origin = origin;
    }

    @Override
    public String toString() {
        String prefix = "";
        StringBuilder sb = new StringBuilder();
        sb.append(origin.length);
        sb.append(":[");
        for (T val : origin) {
            sb.append(prefix);
            prefix = ", ";
            sb.append(val);
        }
        sb.append("]");
        return sb.toString();
    }
}
