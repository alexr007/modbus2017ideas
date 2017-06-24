package jbase.hex;

/**
 * Created by alexr on 18.01.2017.
 */
public class HexFromWords {
    private final int[] origin;

    public HexFromWords(int[] origin) {
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
            sb.append(new HexFromWord(b));
        }
        sb.append("]");
        return sb.toString();
    }
}
