package jbase.hex;

/**
 * Created by alexr on 18.01.2017.
 */
public class WordsAsHex {
    private final int[] origin;

    public WordsAsHex(int[] origin) {
        this.origin = origin;
    }

    @Override
    public String toString() {
        String prefix = "";
        StringBuilder sb = new StringBuilder();
        sb.append(origin.length);
        sb.append(":[");
        for (int b : origin) {
            sb.append(prefix);
            prefix = ", ";
            sb.append(new WordAsHex(b));
        }
        sb.append("]");
        return sb.toString();
    }
}
