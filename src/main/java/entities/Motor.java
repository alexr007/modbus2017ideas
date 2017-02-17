package entities;

import common.hw.ISwitch;
import common.hw.modbus.wad.WAD_Channel;

/**
 * Created by alexr on 07.02.2017.
 */
public class Motor  extends AbstractEntity implements ISwitch {
    private final WAD_Channel channel;

    public Motor (WAD_Channel channel) {
        this.channel = channel;
    }

    public void on() throws Exception {
        channel.on();
    }

    public void off() throws Exception {
        channel.off();
    }
}
