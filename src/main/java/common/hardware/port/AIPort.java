package common.hardware.port;

import common.modbus.wad.ModBusAbstractDevice;

/**
 * Created by alexr on 07.02.2017.
 */
public class AIPort extends Port {
    protected AIPort(ModBusAbstractDevice device, Channel channel) {
        super(device, channel);
    }
}
