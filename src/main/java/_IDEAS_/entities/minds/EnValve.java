package _IDEAS_.entities.minds;

import _IDEAS_.entities.EntityAbstract;
import _IDEAS_.entities.TypeEn;
import _IDEAS_.entities.iface.ISwitch;
import j2bus.modbus.response.ValuesMapped;
import j3wad.channels.WAD_Channel;
import j3wad.chanvalue.ChanValue;

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
