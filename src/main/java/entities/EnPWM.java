package entities;

import common.hw.IPWM;
import common.hw.modbus.wad.WAD_Channel;

/**
 * Created by alexr on 17.02.2017.
 */
public class EnPWM extends AbstractEntity implements IPWM {
    private final WAD_Channel channel;

    public EnPWM(WAD_Channel channel) {
        this.channel = channel;
    }

    @Override
    public void run(int value) throws Exception {
        channel.set(value);
    }
}
