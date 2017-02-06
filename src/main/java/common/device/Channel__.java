package common.device;

/**
 * Created by alexr on 19.01.2017.
 */
public class Channel__ {
    private final int origin;

    public Channel__(int origin) {
        this.origin = origin;
    }

    public int id() {
        return this.origin;
    }
}
