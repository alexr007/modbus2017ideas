package common.hw.port;

import common.hw.modbus.wad.ModBusAbstractDevice;

/**
 * Created by alexr on 07.02.2017.
 */
public abstract class Port {
    protected final ModBusAbstractDevice device;
    protected final Channel channel;

    public Port(ModBusAbstractDevice device, Channel channel) {
        this.device = device;
        this.channel = channel;
    }
}
