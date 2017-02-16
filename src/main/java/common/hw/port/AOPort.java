package common.hw.port;

import common.hw.modbus.response.InvalidModBusResponse;
import common.hw.modbus.response.Values;
import common.hw.modbus.wad.ModBusAbstractDevice;
import common.hw.modbus.wad.ModBusInvalidFunction;
import jssc.SerialPortException;

/**
 * Created by alexr on 07.02.2017.
 */
public class AOPort implements Port {
    private final ModBusAbstractDevice device;
    private final Channel channel;

    public AOPort(ModBusAbstractDevice device, Channel channel) {
        this.device = device;
        this.channel = channel;
    }

    @Override
    public Values get() throws InvalidModBusResponse, SerialPortException, ModBusInvalidFunction {
        return device.channel(channel.get()).get();
    }

    @Override
    public void set(int value) throws Exception {
        device.channel(channel.get()).set(value);
    }

    @Override
    public void on() throws Exception {
        throw new Exception("Invalid Command for AO channel");
    }

    @Override
    public void off() throws Exception {
        throw new Exception("Invalid Command for AO channel");
    }
}
