package _IDEAS_.entities.minds;

import _IDEAS_.entities.EntityAbstract;
import _IDEAS_.entities.TypeEn;
import _IDEAS_.entities.iface.IPWM;
import _IDEAS_.entities.iface.ISwitch;
import j2bus.modbus.response.ValuesMapped;
import j3wad.channels.WAD_Channel;
import j3wad.chanvalue.ChanValue;

/**
 * Created by alexr on 07.02.2017.
 */
public class EnMotorPWM extends EntityAbstract implements ISwitch, IPWM
{
    private final WAD_Channel channel;
    private final WAD_Channel channelValue;

    public EnMotorPWM(WAD_Channel channel, WAD_Channel channelValue) {
        super(TypeEn.MotorPWM);
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

    @Override
    public ValuesMapped<ChanValue> get() {
        return null;
    }
}
