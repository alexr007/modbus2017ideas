package app.web;

/**
 * Created by alexr on 02.05.2017.
 */
public class ValueDOS {
    private final String S_ON="on";
    private final String S_OFF="off";
    private final int I_ON = 1;
    private final int I_OFF = 0;
    private final String origin;

    public ValueDOS(String origin) {
        this.origin = origin;
    }

    public int val() {
        int ret;
        switch (origin.toLowerCase()) {
            case S_ON : ret=I_ON;
                break;
            case S_OFF : ret=I_OFF;
                break;
            default: throw new IllegalArgumentException(String.format("Invalid value:'%s' for chanel.",origin));
        }
        return ret;
    }
}
