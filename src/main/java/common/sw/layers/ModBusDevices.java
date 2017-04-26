package common.sw.layers;

import common.sw.common.IntAsHex;
import common.hw.modbus.ModBus;
import common.hw.modbus.wad.*;
import jssc.SerialPortException;
import org.javatuples.Triplet;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by alexr on 10.02.2017.
 */
public class ModBusDevices {
    private final ModBus modBus;
    private HashMap<String, ModBusAbstractDevice> devices = new HashMap<>();

    public ModBusDevices(ModBus modBus) throws Exception {
        this(modBus, new ArrayList<>());
    }

    public ModBusDevices(ModBus modBus, File source) throws Exception {
        this.modBus = modBus;
        this.devices = new DevicesFromFile(modBus, source).hashMap();
    }

    public ModBusDevices(ModBus modBus, ArrayList<Triplet<String, WADdeviceType, Integer>> devices) throws Exception {
        this.modBus = modBus;
        this.devices = new DevicesFromList(modBus, devices).hashMap();
    }

    public void add(String deviceName, WADdeviceType type, int modbusId) throws Exception {
        if (devices.containsKey(deviceName)) {
            throw new Exception(String.format("Duplicate Module Name:%s",deviceName));
        }
        ModBusAbstractDevice device = ModBusAbstractDevice.build(modBus, type, modbusId);
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("devices list:\n");
        for (HashMap.Entry<String, ModBusAbstractDevice> item : devices.entrySet() ) {
            sb.append(String.format("name: %s, dev: %s\n",item.getKey(),item.getValue().toString()));
        }
        return sb.toString();
    }
}
