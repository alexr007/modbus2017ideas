package entities;

import common.hw.ISensorDigital;
import jbus.modbus.InvalidModBusFunction;
import jbus.modbus.response.InvalidModBusResponse;
import jwad.channels.WAD_Channel;
import jssc.SerialPortException;

/**
 * Created by alexr on 17.02.2017.
 *
 * Концевик
 *
 */
public class EnSensorSwitch extends AbstractEntity implements ISensorDigital{
    private final WAD_Channel channel;

    public EnSensorSwitch(WAD_Channel channel) {
        this.channel = channel;
    }

    @Override
    public boolean fail() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        return channel.fail().get() == 1;
    }

    @Override
    public boolean opened() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        return channel.get().get() == 0;
    }

    @Override
    public boolean closed() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        return channel.get().get() == 1;
    }
}
