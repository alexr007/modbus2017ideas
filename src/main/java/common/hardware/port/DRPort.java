package common.hardware.port;

import common.modbus.wad.ModBusAbstractDevice;
import common.modbus.wad.ModBusInvalidFunction;
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
public class DRPort extends Port {
    public DRPort(ModBusAbstractDevice device, Channel channel) {
        super(device, channel);
    }

    public void on() throws SerialPortException, ModBusInvalidFunction {
        device.channel(channel.get()).on();
    }

    public void off() throws SerialPortException, ModBusInvalidFunction {
        device.channel(channel.get()).off();
    }
}
