package common.modbus.wad;

import common.modbus.device.DeviceProperties;
import common.modbus.device.PortType;
import common.modbus.device.SignalType;
import persistence.ModBus;

/**
 * Created by alexr on 06.02.2017.
 */
final public class WAD_DI14_BUS extends ModBusAbstractDevice implements WAD_func_channel {
    /*
     * inherited:
     *
     * private final ModBus modbus;
     * protected final ModBusRequestBuilder builder;
     * protected final int deviceId;
     *
     */
    public WAD_DI14_BUS(ModBus modbus, int deviceId) {
        super(modbus, deviceId);
        properties = new DeviceProperties(SignalType.Digital, PortType.Input, 15);
    }

    @Override
    public WAD_Channel channel(int chan) {
        return new WAD_DI14_Channel(chan, this);
    }
}
