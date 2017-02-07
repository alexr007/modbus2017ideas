package common.hardware.port;

/**
 * Created by alexr on 07.02.2017.
 */
public class Channel {
    private final int channel;

    public Channel(int channel) {
        this.channel = channel;
    }

    public int get() {
        return channel;
    }
}
