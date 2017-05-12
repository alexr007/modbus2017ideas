package entities;

import common.hw.ISwitch;
import jwad.channels.WAD_Channel;

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
public class EnValve extends AbstractEntity implements ISwitch {
    private final WAD_Channel channel;

    public EnValve(WAD_Channel channel) {
        this.channel = channel;
    }

    public void on() throws Exception {
        channel.on();
    }

    public void off() throws Exception {
        channel.off();
    }
}
