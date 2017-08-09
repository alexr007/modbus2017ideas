package entities.minds;

import entities.EntityAbstract;
import entities.TypeEn;
import entities.iface.ISwitch;
import jbus.modbus.response.ValuesMapped;
import jwad.channels.WAD_Channel;
import jwad.chanvalue.ChanValue;

/**
 * Created by alexr on 07.02.2017.
 *
 * Реализация клапана
 * ========================
 *
 * клапан может быть:
 * - открыт
 * - зкрыт
 *
 */
public class EnValve extends EntityAbstract implements ISwitch {
    private final WAD_Channel channel;

    public EnValve(WAD_Channel channel) {
        super(TypeEn.Valve);
        this.channel = channel;
    }

    @Override
    public void on() throws Exception {

    }

    @Override
    public void off() throws Exception {

    }

    @Override
    public ValuesMapped<ChanValue> get() {
        return null;
    }
}
