package jbase.hex;

/**
 * Created by alexr on 18.01.2017.
 *
 * print word
 * in HEX View
 *
 * aa -> 0x0001
 * 255 -> 0x00FF
 * 65535 --> 0xFFFF
 *
 * if origin > 65535 - only 2 lower bytes is used
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
