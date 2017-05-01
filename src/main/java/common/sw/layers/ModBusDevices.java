package common.sw.layers;

import common.sw.common.IntAsHex;
import common.hw.modbus.ModBus;
import common.hw.modbus.wad.*;
import jssc.SerialPortException;
import org.javatuples.Triplet;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by alexr on 10.02.2017.
 */
public class ModBusDevices {
    private final ModBus modBus;
    private HashMap<CharSequence, ModBusAbstractDevice> devices = new HashMap<>();

    public ModBusDevices(ModBus modBus) throws Exception {
        this(modBus, new ArrayList<>());
    }

    public ModBusDevices(ModBus modBus, File source) throws Exception {
        this.modBus = modBus;
        this.devices = new DevicesFromFile(modBus, source).hashMap();
    }

    public ModBusDevices(ModBus modBus, ArrayList<Triplet<String, WadDevType, Integer>> devices) throws Exception {
        this.modBus = modBus;
        this.devices = new DevicesFromList(modBus, devices).hashMap();
    }

    public void add(String deviceName, WadDevType type, int modbusId) throws Exception {
        if (devices.containsKey(deviceName)) {
            throw new Exception(String.format("Duplicate Module Name:%s",deviceName));
        }
        ModBusAbstractDevice device = ModBusAbstractDevice.build(modBus, type, modbusId);
        // эта хрень не работает
        // TODO: переписать equals & hashCode что бы избежать ошибок при конфигурировании
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

    public ModBusAbstractDevice get(CharSequence deviceName) throws Exception {
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
        devices.forEach((k, v)->
            sb.append(String.format("name: %s, dev: %s, properties:%s\n",
                k,
                v.toString(),
                v.properties.toString()
            )));
        return sb.toString();
    }

    public Set<CharSequence> list() {
        return devices.keySet();
    }
}
