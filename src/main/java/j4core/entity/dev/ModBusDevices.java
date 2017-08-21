package j4core.entity.dev;

import j4core.entity.MapFrom;
import constants.DevName;
import j1base.hex.HexFromByte;
import j2bus.modbus.ModBus;
import j3wad.modules.WadAbstractDevice;
import j3wad.WadDevType;
import jssc.SerialPortException;
import org.javatuples.Triplet;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by alexr on 10.02.2017.
 */
public final class ModBusDevices {
    private final String ERROR_GET = "Module Name NotFound:%s";
    private final String ERROR_ADD_K = "Duplicate Module Name:%s";
    private final String ERROR_ADD_V = "Duplicate ModBus Device:%s id:%s";

    private final ModBus modBus;
    private final Map<DevName, WadAbstractDevice> devices;
    private final Map<Integer, WadAbstractDevice> devicesById = new HashMap<>();

    /** ctor 1 - empty set */
    public ModBusDevices(final ModBus modBus) throws Exception {
        this(modBus, new ArrayList<>());
    }

    /** ctor 2 - random quantity Triplet<DevName, WadDevType, Integer> */
    public ModBusDevices(final ModBus modBus, final Triplet<DevName, WadDevType, Integer>... devices) throws Exception {
        this(modBus, new ArrayList<>(Arrays.asList(devices)));
    }

    /** ctor 3 - ArrayList<Triplet<DevName, WadDevType, Integer>> */
    public ModBusDevices(final ModBus modBus, final List<Triplet<DevName, WadDevType, Integer>> devices) throws Exception {
        this(modBus, new DevMapFromList(modBus, devices));
    }

    /** ctor 4 - MapFrom */
    public ModBusDevices(final ModBus modBus, final MapFrom<DevName, WadAbstractDevice> devices) throws Exception {
        this(modBus, devices.map());
    }

    /** ctor 5 - plain assignment */
    public ModBusDevices(final ModBus modBus, final Map<DevName, WadAbstractDevice> devices) throws Exception {
        this.modBus = modBus;
        this.devices = devices;
        /**
         * one more map for access to WADdevice by ModBusId
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

    public void finish() throws IOException {
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

    /**
     *
     * @return
     * ArrayList<Triplet<DevName, WadDevType, CharSequence>>
     *    device name (DevName)
     *    device type (WadDevType)
     *    modbusId (String)
     */
    public List<Triplet> listDetailsTriplet() {
        return devices.entrySet().stream()
            .map(ent ->new Triplet<DevName, WadDevType, CharSequence>(
                ent.getKey(), // device name (DevName)
                ent.getValue().type(), // device type (WadDevType)
                new HexFromByte(ent.getValue().id()).toString() // modbusId (String)
            ))
            .collect(Collectors.toList());
    }
}
