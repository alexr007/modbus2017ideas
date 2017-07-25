package entities;

import jwad.channels.WAD_Channel;

/**
 * Created by alexr on 17.02.2017.
 */
public class EnPWM extends AbstractEntity implements IPWM {
    private final WAD_Channel channel;

    public EnPWM(WAD_Channel channel) {
        super(EntityType.PWM);
        this.channel = channel;
    }

    @Override
    public void run(int value) throws Exception {
        channel.set(value);
    }
}
