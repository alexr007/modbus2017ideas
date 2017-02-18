package entities;

import common.hw.ISensorAnalog;
import common.hw.modbus.response.InvalidModBusResponse;
import common.hw.modbus.response.Values;
import common.hw.modbus.wad.ModBusInvalidFunction;
import common.hw.modbus.wad.WAD_Channel;
import jssc.SerialPortException;

/**
 * Created by alexr on 17.02.2017.
 */
public class EnSensor extends AbstractEntity implements ISensorAnalog {
    private final WAD_Channel channel;

    public EnSensor(WAD_Channel channel) {
        this.channel = channel;
    }

    @Override
    public boolean fail() throws InvalidModBusResponse, ModBusInvalidFunction, SerialPortException {
        return channel.fail().get() == 1;
    }
    @Override
    public Values get() throws InvalidModBusResponse, SerialPortException, ModBusInvalidFunction {
        return channel.get();
    }
}
