package entities;

import common.hw.ISensorDigital;
import common.hw.modbus.response.InvalidModBusResponse;
import common.hw.modbus.wad.ModBusInvalidFunction;
import common.hw.modbus.wad.WAD_Channel;
import jssc.SerialPortException;

/**
 * Created by alexr on 17.02.2017.
 *
 * Концевик
 *
 */
public class SensorSwitch  extends AbstractEntity implements ISensorDigital{
    private final WAD_Channel channel;

    public SensorSwitch(WAD_Channel channel) {
        this.channel = channel;
    }

    @Override
    public boolean fail() throws InvalidModBusResponse, ModBusInvalidFunction, SerialPortException {
        return channel.fail().get() == 1;
    }

    @Override
    public boolean opened() throws InvalidModBusResponse, SerialPortException, ModBusInvalidFunction {
        return channel.get().get() == 0;
    }

    @Override
    public boolean closed() throws InvalidModBusResponse, SerialPortException, ModBusInvalidFunction {
        return channel.get().get() == 1;
    }
}
