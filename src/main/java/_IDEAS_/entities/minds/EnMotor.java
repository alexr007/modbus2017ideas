package _IDEAS_.entities.minds;

import _IDEAS_.entities.EntityAbstract;
import _IDEAS_.entities.TypeEn;
import _IDEAS_.entities.iface.ISwitch;
import j2bus.modbus.response.ValuesMapped;
import j3wad.channels.WAD_Channel;
import j3wad.chanvalue.ChanValue;

/**
 * Created by alexr on 07.02.2017.
 */
public class EnMotor extends EntityAbstract implements ISwitch {
    private final WAD_Channel channel;

    public EnMotor(WAD_Channel channel) {
        super(TypeEn.Motor);
        this.channel = channel;
    }

    public void on() throws Exception {
        channel.on();
    }

    public void off() throws Exception {
        channel.off();
    }

    @Override
    public ValuesMapped<ChanValue> get() {
        return null;
    }
}
