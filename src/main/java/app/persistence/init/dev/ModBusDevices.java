package app.persistence.init.dev;

import app.persistence.init.HashMapFrom;
import constants.DevName;
import jbase.hex.HexFromByte;
import jbus.modbus.ModBus;
import jwad.modules.WadAbstractDevice;
import jwad.WadDevType;
import jssc.SerialPortException;
import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by alexr on 10.02.2017.
 */
public class ModBusDevices {
    private final ModBus modBus;
    private final HashMap<DevName, WadAbstractDevice> devices;

    // ctor 1 - empty list
    public ModBusDevices(final ModBus modBus) throws Exception {
        this(modBus, new ArrayList<>());
    }

    // ctor 2 - random quantity Triplet<DevName, WadDevType, Integer>
    public ModBusDevices(final ModBus modBus, final Triplet<DevName, WadDevType, Integer>... devices) throws Exception {
        this(modBus, new ArrayList<>(Arrays.asList(devices)));
    }

    // ctor 3 - ArrayList<Triplet<DevName, WadDevType, Integer>>
    public ModBusDevices(final ModBus modBus, final ArrayList<Triplet<DevName, WadDevType, Integer>> devices) throws Exception {
        this(modBus, new DevicesFromList(modBus, devices));
    }

    // ctor 4 - HashMapFrom
    public ModBusDevices(final ModBus modBus, final HashMapFrom<DevName, WadAbstractDevice> devices) throws Exception {
        this(modBus, devices.hashMap());
    }

    // ctor 5 - plain assignment
    public ModBusDevices(final ModBus modBus, final HashMap<DevName, WadAbstractDevice> devices) throws Exception {
        this.modBus = modBus;
        this.devices = devices;
    }

    public void add(DevName deviceName, WadDevType type, int modbusId) throws Exception {
        if (devices.containsKey(deviceName)) {
            throw new Exception(String.format("Duplicate Module Name:%s",deviceName.toString()));
        }
        WadAbstractDevice device = WadAbstractDevice.build(modBus, type, modbusId);
        if (devices.containsValue(device)) {
            throw new Exception(
                String.format("Duplicate ModBus Device:%s id:%s",
                    type.toString(),
                    new HexFromByte(modbusId).toString()
                )
            );
        }
        devices.put(deviceName, device);
    }

    public WadAbstractDevice get(DevName deviceName) throws Exception {
        if (!devices.containsKey(deviceName)) {
            throw new Exception(String.format("Module Name NotFound:%s", deviceName.toString()));
        }
        return devices.get(deviceName);
    }

    public WadAbstractDevice get(String deviceName) throws Exception {
        if (!devices.containsKey(DevName.valueOf(deviceName))) {
            throw new Exception(String.format("Module Name NotFound:%s", deviceName));
        }
        return devices.get(DevName.valueOf(deviceName));
    }

    public void finish() throws SerialPortException {
        modBus.finish();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("devices list:\n");
        devices.forEach((k, v)->
            sb.append(String.format("dev.name: %s, dev: %s, dev.prop:%s\n",
                k.toString(), // enum key from HashMap
                v.toString(), // modbus type AIT,DOS etc + modbusId
                v.properties().toString() // dev.prop: signalType, portType, chanCount
            )));
        return sb.toString();
    }

    public Set<DevName> list() {
        return devices.keySet();
    }

    /**
     *
     * @return
     * ArrayList<Triplet<DevName, WadDevType, CharSequence>>
     *    device name (DevName)
     *    device type (WadDevType)
     *    modbusId (String)
     */
    public ArrayList<Triplet> triplet() {
        ArrayList<Triplet> list = new ArrayList<>();
        devices.forEach((key, dev) -> {
            list.add(new Triplet<DevName, WadDevType, CharSequence>(
                key, // device name (DevName)
                dev.type(), // device type (WadDevType)
                new HexFromByte(dev.id()).toString() // modbusId (String)
            ));
        });
        return list;
    }
}
