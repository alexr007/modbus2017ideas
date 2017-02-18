package entities;

import common.hw.IPWM;
import common.hw.modbus.response.InvalidModBusResponse;
import common.hw.modbus.response.Values;
import common.hw.modbus.wad.ModBusInvalidFunction;
import common.hw.modbus.wad.WAD_Channel;
import jssc.SerialPortException;

/**
 * Created by alexr on 17.02.2017.
 */
public class EnPWM extends AbstractEntity implements IPWM {
    private final WAD_Channel channel;

    public EnPWM(WAD_Channel channel) {
        this.channel = channel;
    }

    @Override
    public void run(int value) throws Exception {
        channel.set(value);
    }
}
