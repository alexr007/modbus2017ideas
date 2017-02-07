package common.hardware.port;

import common.modbus.wad.ModBusAbstractDevice;
import common.modbus.wad.ModBusInvalidFunction;
import jssc.SerialPortException;

/**
 * Created by alexr on 07.02.2017.
 */
public class AOPort extends Port {
    public AOPort(ModBusAbstractDevice device, Channel channel) {
        super(device, channel);
    }

    public void run (int value) throws SerialPortException, ModBusInvalidFunction {
        device.channel(channel.get()).set(value);
    }
}
