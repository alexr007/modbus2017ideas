package entities;

import jwad.channels.WAD_Channel;

/**
 * Created by alexr on 07.02.2017.
 */
public class EnMotor extends AbstractEntity implements ISwitch {
    private final WAD_Channel channel;

    public EnMotor(WAD_Channel channel) {
        this.channel = channel;
    }

    public void on() throws Exception {
        channel.on();
    }

    public void off() throws Exception {
        channel.off();
    }
}
