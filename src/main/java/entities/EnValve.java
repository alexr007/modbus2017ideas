package entities;

import common.hw.ISwitch;
import common.hw.modbus.response.InvalidModBusResponse;
import common.hw.modbus.response.Values;
import common.hw.modbus.wad.ModBusInvalidFunction;
import common.hw.modbus.wad.WAD_Channel;
import jssc.SerialPortException;

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
