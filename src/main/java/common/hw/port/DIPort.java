package common.hw.port;

import common.hw.modbus.wad.ModBusAbstractDevice;

/**
 * Created by alexr on 07.02.2017.
 */
public class DIPort extends Port {
    protected DIPort(ModBusAbstractDevice device, Channel channel) {
        super(device, channel);
    }
}
