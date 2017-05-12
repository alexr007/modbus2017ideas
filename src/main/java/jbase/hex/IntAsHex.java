package jbase.hex;

/**
 * Created by alexr on 15.01.2017.
 *
 * print integer
 * in HEX View
 *
 * aa -> 0x01
 * 255 -> 0xFF
 *
 * if origin > 255 - only lower byte is used
 */
public class IntAsHex {
    private final int origin;

    public IntAsHex(int origin) {
        this.origin = 0xFF & origin;
    }

    @Override
    public String toString() {
        return String.format("0x%02X", origin);
    }
}
