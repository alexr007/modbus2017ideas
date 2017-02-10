package common.sw;

import common.hw.modbus.ModBus;
import common.hw.modbus.wad.*;
import common.hw.port.Channel;
import jssc.SerialPortException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by alexr on 10.02.2017.
 */
public class ModBusDevices {
    private final ModBus modBus;
    private HashMap<String, ModBusAbstractDevice> devices = new HashMap<>();

    public ModBusDevices(ModBus modBus) {
        this.modBus = modBus;
    }

    public void add(String deviceName, WADdeviceType type, int channel) throws Exception {
        if (findDuplicates(deviceName)) {
            throw new Exception("duplicate module name");
        }
        ModBusAbstractDevice device = null;
        switch (type) {
            case AIK: device = new WAD_AIK_BUS(modBus, channel);
                break;
            case AO: device = new WAD_AO_BUS(modBus, channel);
                break;
            case DI: device = new WAD_DI_BUS(modBus, channel);
                break;
            case DI14: device = new WAD_DI14_BUS(modBus, channel);
                break;
            case DOS: device = new WAD_DOS_BUS(modBus, channel);
                break;
            default: break;
        }
        devices.put(deviceName, device);
    }

    public ModBusAbstractDevice get(String deviceName) {
        return devices.get(deviceName);
    }

    private boolean findDuplicates(String deviceName) {
        return false;
    }

    public void finish() throws SerialPortException {
        modBus.finish();
    }
}
