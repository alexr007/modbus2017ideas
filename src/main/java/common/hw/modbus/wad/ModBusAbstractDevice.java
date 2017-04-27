package common.hw.modbus.wad;

import common.sw.common.IntAsHex;
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
            String.format("%-13s id: %s",
                this.getClass().getSimpleName(),
                new IntAsHex(deviceId).toString()
            );
    }

    public static ModBusAbstractDevice build(ModBus modBus, WadDevType type, int modbusId) {
        ModBusAbstractDevice device = null;
        switch (type) {
            case AIK: device = new WAD_AIK_BUS(modBus, modbusId);
                break;
            case AO: device = new WAD_AO_BUS(modBus, modbusId);
                break;
            case AO6: device = new WAD_AO6_BUS(modBus, modbusId);
                break;
            case DI: device = new WAD_DI_BUS(modBus, modbusId);
                break;
            case DI14: device = new WAD_DI14_BUS(modBus, modbusId);
                break;
            case DOS: device = new WAD_DOS_BUS(modBus, modbusId);
                break;
            default: break;
        }
        return device;
    }

}
