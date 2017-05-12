package entities;

import common.hw.ISwitch;
import jwad.channels.WAD_Channel;

/**
 * Created by alexr on 17.02.2017.
 */
public class EnRelay extends AbstractEntity implements ISwitch{
    private final WAD_Channel channel;

    public EnRelay(WAD_Channel channel) {
        this.channel = channel;
    }

    public void on() throws Exception {
        channel.on();
    }

    public void off() throws Exception {
        channel.off();
    }
}
