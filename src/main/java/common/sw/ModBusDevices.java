package common.sw;

import common.IntAsHex;
import common.hw.modbus.ModBus;
import common.hw.modbus.wad.*;
import jssc.SerialPortException;
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

    public void add(String deviceName, WADdeviceType type, int modbusId) throws Exception {
        if (devices.containsKey(deviceName)) {
            throw new Exception(String.format("Duplicate Module Name:%s",deviceName));
        }
        ModBusAbstractDevice device = null;
        switch (type) {
            case AIK: device = new WAD_AIK_BUS(modBus, modbusId);
                break;
            case AO: device = new WAD_AO_BUS(modBus, modbusId);
                break;
            case DI: device = new WAD_DI_BUS(modBus, modbusId);
                break;
            case DI14: device = new WAD_DI14_BUS(modBus, modbusId);
                break;
            case DOS: device = new WAD_DOS_BUS(modBus, modbusId);
                break;
            default: break;
        }
        // эта хрень не работает
        // TODO: переписать equals & hashcode
        if (devices.containsValue(device)) {
            throw new Exception(
                String.format("Duplicate ModBus Device:%s id:%s",
                    type.toString(),
                    new IntAsHex(modbusId).toString()
                )
            );
        }
        devices.put(deviceName, device);
    }

    public ModBusAbstractDevice get(String deviceName) throws Exception {
        if (!devices.containsKey(deviceName)) {
            throw new Exception(String.format("Module Name NotFound:%s",deviceName));
        }
        return devices.get(deviceName);
    }

    public void finish() throws SerialPortException {
        modBus.finish();
    }
}
