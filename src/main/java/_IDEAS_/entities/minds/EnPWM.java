package _IDEAS_.entities.minds;

import _IDEAS_.entities.EntityAbstract;
import _IDEAS_.entities.TypeEn;
import _IDEAS_.entities.iface.IPWM;
import j3wad.channels.WAD_Channel;

/**
 * Created by alexr on 17.02.2017.
 */
public class EnPWM extends EntityAbstract implements IPWM {
    private final WAD_Channel channel;

    public EnPWM(WAD_Channel channel) {
        super(TypeEn.PWM);
        this.channel = channel;
    }

    @Override
    public void run(int value) throws Exception {
        channel.set(value);
    }
}
