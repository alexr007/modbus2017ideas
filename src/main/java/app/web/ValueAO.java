package app.web;

/**
 * Created by alexr on 02.05.2017.
 */
public class ValueAO {
    private final String origin;

    public ValueAO(String origin) {
        this.origin = origin;
    }

    public int val() {
        int value = Integer.valueOf(origin);
        if (value >= 0 && value<65535 )
            return value;
        else
            throw new IllegalArgumentException(String.format("Invalid value:'%s' for chanel.",origin));
    }
}
