package common.hw.port;

import common.hw.modbus.response.InvalidModBusResponse;
import common.hw.modbus.response.Values;
import common.hw.modbus.wad.ModBusAbstractDevice;
import common.hw.modbus.wad.ModBusInvalidFunction;
import jssc.SerialPortException;

/**
 * Created by alexr on 07.02.2017.
 *
 * TODO:
 * здесь надо инкапсулировать
 * порт WAD-BUS устройства
 * и собственно с ним работать
 *
 */
public class DRPort implements Port {
    private final ModBusAbstractDevice device;
    private final Channel channel;

    public DRPort(ModBusAbstractDevice device, Channel channel) {
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
        device.channel(channel.get()).on();
    }

    @Override
    public void off() throws Exception {
        device.channel(channel.get()).off();
    }
}
