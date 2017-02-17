package entities;

import common.hw.ISwitch;
import common.hw.modbus.wad.WAD_Channel;

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
public class Valve  extends AbstractEntity implements ISwitch {
    private final WAD_Channel channel;

    public Valve(WAD_Channel channel) {
        this.channel = channel;
    }

    public void on() throws Exception {
        channel.on();
    }

    public void off() throws Exception {
        channel.off();
    }
}
