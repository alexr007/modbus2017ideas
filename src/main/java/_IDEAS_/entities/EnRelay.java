package _IDEAS_.entities;

import _IDEAS_.entities.iface.ISwitch;
import j2bus.modbus.response.ValuesMapped;
import j3wad.channels.WAD_Channel;
import j3wad.chanvalue.ChanValue;

/**
 * Created by alexr on 17.02.2017.
 */
public class EnRelay extends EntityAbstract implements ISwitch {
    private final WAD_Channel channel;

    public EnRelay(WAD_Channel channel) {
        super(TypeEn.Relay);
        this.channel = channel;
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
        return channel.get();
    }
}
