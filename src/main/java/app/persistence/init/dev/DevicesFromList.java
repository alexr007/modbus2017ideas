package app.persistence.init.dev;

import app.persistence.init.EnumMapFrom;
import constants.DevName;
import jbus.modbus.ModBus;
import jwad.modules.WadAbstractDevice;
import jwad.WadDevType;
import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;

/**
 * Created by alexr on 26.04.2017.
 */
public class DevicesFromList implements EnumMapFrom<DevName, WadAbstractDevice> {
    private final ModBus modBus;
    private final ArrayList<Triplet<DevName, WadDevType, Integer>> devicesList;

    public DevicesFromList(final ModBus modBus, final Triplet<DevName, WadDevType, Integer>... list) {
        this(modBus, new ArrayList<>(Arrays.asList(list)));
    }

    public DevicesFromList(final ModBus modBus, final ArrayList<Triplet<DevName, WadDevType, Integer>> list) {
        this.modBus = modBus;
        this.devicesList = list;
    }

    @Override
    public EnumMap<DevName, WadAbstractDevice> enumMap() throws Exception {
        final EnumMap<DevName, WadAbstractDevice> map = new EnumMap<>(DevName.class);
        for (Triplet<DevName, WadDevType, Integer> item : devicesList) {
            if (map.containsKey(item.getValue0())) {
                throw new Exception(String.format("Duplicate Device name: %s", item.getValue0().toString()));
            }
            map.put(
                item.getValue0(),
                WadAbstractDevice.build(modBus, item.getValue1(), item.getValue2())
            );
        }
        return map;
    }
}
