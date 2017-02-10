package common.hw.modbus.wad;

import common.hw.modbus.ModBusRequestBuilder;
import common.hw.modbus.device.DeviceProperties;
import jssc.SerialPortException;
import common.hw.modbus.MbRequest;
import common.hw.modbus.MbResponse;
import common.hw.modbus.ModBus;

public abstract class ModBusAbstractDevice {
    private final ModBus modbus;
    protected final int deviceId;
    protected final ModBusRequestBuilder builder;
    public DeviceProperties properties;

    public ModBusAbstractDevice(ModBus modbus, int deviceId) {
        this.modbus = modbus;
        this.deviceId = deviceId;
        this.builder = new ModBusRequestBuilder(deviceId);
    }

    public abstract WAD_Channel channel(int chan);

    public MbResponse run(MbRequest req) throws SerialPortException {
        return modbus.run(req);
    }
}
