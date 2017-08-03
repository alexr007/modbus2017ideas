package app.persistence.init.dev;

import app.persistence.init.EnumMapFrom;
import constants.DevName;
import jbase.hex.HexFromByte;
import jbus.modbus.ModBus;
import jwad.modules.WadAbstractDevice;
import jwad.WadDevType;
import jssc.SerialPortException;
import org.javatuples.Triplet;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by alexr on 10.02.2017.
 */
public final class ModBusDevices {
    private final String ERROR_GET = "Module Name NotFound:%s";
    private final String ERROR_ADD_K = "Duplicate Module Name:%s";
    private final String ERROR_ADD_V = "Duplicate ModBus Device:%s id:%s";

    private final ModBus modBus;
    private final EnumMap<DevName, WadAbstractDevice> devices;
    private final HashMap<Integer, WadAbstractDevice> devicesById = new HashMap<>();

    /** ctor 1 - empty set */
    public ModBusDevices(final ModBus modBus) throws Exception {
        this(modBus, new ArrayList<>());
    }

    /** ctor 2 - random quantity Triplet<DevName, WadDevType, Integer> */
    public ModBusDevices(final ModBus modBus, final Triplet<DevName, WadDevType, Integer>... devices) throws Exception {
        this(modBus, new ArrayList<>(Arrays.asList(devices)));
    }

    /** ctor 3 - ArrayList<Triplet<DevName, WadDevType, Integer>> */
    public ModBusDevices(final ModBus modBus, final ArrayList<Triplet<DevName, WadDevType, Integer>> devices) throws Exception {
        this(modBus, new DevicesFromList(modBus, devices));
    }

    /** ctor 4 - EnumMapFrom */
    public ModBusDevices(final ModBus modBus, final EnumMapFrom<DevName, WadAbstractDevice> devices) throws Exception {
        this(modBus, devices.enumMap());
    }

    /** ctor 5 - plain assignment */
    public ModBusDevices(final ModBus modBus, final EnumMap<DevName, WadAbstractDevice> devices) throws Exception {
        this.modBus = modBus;
        this.devices = devices;
        /**
         * one more enumMap for access to WADdevice by ModBusId
         */
        this.devices.forEach(
            (k,v) -> devicesById.put(v.id(), v)
        );
    }

    /** try to catch Exception if possible */
    public void add(DevName deviceName, WadDevType type, int modbusId) {
        try {
            addVerbose(deviceName, type, modbusId);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void addVerbose(DevName deviceName, WadDevType type, int modbusId) throws Exception {
        if (devices.containsKey(deviceName)) {
            throw new Exception(
                String.format(ERROR_ADD_K, deviceName.toString()));
        }
        WadAbstractDevice device = WadAbstractDevice.build(modBus, type, modbusId);
        if (devices.containsValue(device)) {
            throw new Exception(
                String.format(ERROR_ADD_V, type.toString(), new HexFromByte(modbusId).toString())
            );
        }
        devices.put(deviceName, device);
    }

    /** try to catch Exception if possible */
    public WadAbstractDevice get(DevName deviceName) {
        try {
            return getVerbose(deviceName);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    /** try to catch Exception if possible */
    public WadAbstractDevice get(String deviceName) {
        try {
            return getVerbose(deviceName);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    /** try to catch Exception if possible */
    public WadAbstractDevice get(int deviceId) {
        try {
            return getVerbose(deviceId);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public WadAbstractDevice getVerbose(String deviceName) throws Exception {
        return getVerbose(DevName.valueOf(deviceName));
    }

    public WadAbstractDevice getVerbose(int deviceId) throws Exception {
        if (!devicesById.containsKey(deviceId)) {
            throw new Exception(String.format(ERROR_GET, deviceId));
        }
        return devicesById.get(deviceId);
    }

    public WadAbstractDevice getVerbose(DevName deviceName) throws Exception {
        if (!devices.containsKey(deviceName)) {
            throw new Exception(String.format(ERROR_GET, deviceName.toString()));
        }
        return devices.get(deviceName);
    }

    public void finish() throws SerialPortException {
        modBus.finish();
    }

    @Override
    public String toString() {
        return String.format("Devices configured (HashMap):\n%s",
            devices.entrySet().stream()
                .map(ent -> String.format("dev.name: %-4s, dev: %s, dev.prop:%s\n",
                    ent.getKey().toString(), // enum key from HashMap
                    ent.getValue().toString(), // modbus type AIT,DOS etc + modbusId
                    ent.getValue().properties().toString() // dev.prop: signalType, portType, chanCount
                ))
                .collect(Collectors.joining()));
    }

    public Set<DevName> set() {
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
