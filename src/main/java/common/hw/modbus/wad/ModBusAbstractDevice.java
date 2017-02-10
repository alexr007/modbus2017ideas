package common.hw.modbus.wad;

import common.IntAsHex;
import common.hw.modbus.*;
import common.hw.modbus.device.DeviceProperties;
import common.hw.modbus.response.InvalidModBusResponse;
import jssc.SerialPortException;

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
    public abstract int temperature() throws SerialPortException, InvalidModBusResponse, InvalidModBusFunction;

    public MbResponse run(MbRequest req) throws SerialPortException {
        return modbus.run(req);
    }

    @Override
    public String toString() {
        return
            this.getClass().toString()
            +" id:"+new IntAsHex(deviceId).toString();
    }

}
