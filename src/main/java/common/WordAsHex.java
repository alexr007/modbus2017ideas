package common;

/**
 * Created by alexr on 18.01.2017.
 */
public class WordAsHex {
    private final int origin;

    public WordAsHex(int origin) {
        this.origin = 0xFFFF & origin;
    }

    @Override
    public String toString() {
        return String.format("0x%04X", origin);
    }
}
