package app.persistence.init.dev;

import app.persistence.init.HashMapFrom;
import constants.DevName;
import jbus.modbus.ModBus;
import jwad.modules.WadAbstractDevice;
import jwad.WadDevType;
import org.javatuples.Triplet;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by alexr on 26.04.2017.
 */
public class DevicesFromList implements HashMapFrom<DevName, WadAbstractDevice> {
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
    public HashMap<DevName, WadAbstractDevice> hashMap() throws Exception {
        final HashMap<DevName, WadAbstractDevice> map = new HashMap<>();
/*
    stream implementation. need explicit Exception handling
        devicesList.forEach(item -> {
                    if (map.containsKey(item.getValue0()))
                        throw new Exception (String.format("Duplicate Device name: %s", item.getValue0().toString()));
                    map.put(
                        item.getValue0(),
                        WadAbstractDevice.build(modBus, item.getValue1(), item.getValue2())
                    );
        });
*/
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
