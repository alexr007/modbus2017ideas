package entities.minds;

import entities.EntityAbstract;
import entities.TypeEn;
import entities.iface.IPWM;
import jwad.channels.WAD_Channel;

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
