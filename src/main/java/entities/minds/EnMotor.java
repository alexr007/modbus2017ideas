package entities.minds;

import entities.EntityAbstract;
import entities.TypeEn;
import entities.iface.ISwitch;
import jbus.modbus.response.ValuesMapped;
import jwad.channels.WAD_Channel;
import jwad.chanvalue.ChanValue;

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
