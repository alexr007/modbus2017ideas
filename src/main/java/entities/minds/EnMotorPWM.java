package entities.minds;

import entities.EntityAbstract;
import entities.TypeEn;
import entities.iface.IPWM;
import entities.iface.ISwitch;
import jbus.modbus.response.ValuesMapped;
import jwad.channels.WAD_Channel;
import jwad.chanvalue.ChanValue;

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
