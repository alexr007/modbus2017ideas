package entities;

import common.hw.IPWM;
import common.hw.ISwitch;
import common.hw.modbus.wad.WAD_Channel;

/**
 * Created by alexr on 07.02.2017.
 */
public class MotorPWM  extends AbstractEntity implements ISwitch, IPWM
{
    private final WAD_Channel channel;
    private final WAD_Channel channelValue;

    public MotorPWM(WAD_Channel channel, WAD_Channel channelValue) {
        this.channel = channel;
        this.channelValue = channelValue;
    }

    @Override
    public void run(int value) throws Exception {
        channelValue.set(value);
    }

    @Override
    public void on() throws Exception {
        channel.on();
    }

    @Override
    public void off() throws Exception {
        channel.off();
    }
}
