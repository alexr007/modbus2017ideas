package j4core.entity.dev;

import j4core.entity.MapFrom;
import constants.DevName;
import j2bus.modbus.ModBus;
import j3wad.modules.WadAbstractDevice;
import j3wad.WadDevType;
import org.javatuples.Triplet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

/**
 * Created by alexr on 26.04.2017.
 */
public final class DevMapFromList implements MapFrom<DevName, WadAbstractDevice> {
    private final ModBus modBus;
    private final List<Triplet<DevName, WadDevType, Integer>> devicesList;

    public DevMapFromList(final ModBus modBus, final Triplet<DevName, WadDevType, Integer>... list) {
        this(modBus, new ArrayList<>(Arrays.asList(list)));
    }

    public DevMapFromList(final ModBus modBus, final List<Triplet<DevName, WadDevType, Integer>> list) {
        this.modBus = modBus;
        this.devicesList = list;
    }

    @Override
    public EnumMap<DevName, WadAbstractDevice> map()  {
        final EnumMap<DevName, WadAbstractDevice> map = new EnumMap<>(DevName.class);
        for (Triplet<DevName, WadDevType, Integer> item : devicesList) {
            if (map.containsKey(item.getValue0())) {
                throw new IllegalArgumentException(String.format("Duplicate Device name: %s", item.getValue0().toString()));
            }
            map.put(
                item.getValue0(),
                WadAbstractDevice.build(modBus, item.getValue1(), item.getValue2())
            );
        }
        return map;
    }
}
