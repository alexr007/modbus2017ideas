package common.hardware.port;

import common.modbus.wad.ModBusAbstractDevice;

/**
 * Created by alexr on 07.02.2017.
 */
public class DIPort extends Port {
    protected DIPort(ModBusAbstractDevice device, Channel channel) {
        super(device, channel);
    }
}
