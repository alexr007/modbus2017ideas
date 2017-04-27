package common.sw.layers;

import common.hw.modbus.ModBus;
import common.hw.modbus.wad.ModBusAbstractDevice;
import common.hw.modbus.wad.WADdeviceType;
import org.javatuples.Triplet;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by alexr on 26.04.2017.
 */
public class DevicesFromList {
    private final ModBus modBus;
    private final ArrayList<Triplet<String, WADdeviceType, Integer>> devicesList;

    public DevicesFromList(ModBus modBus, ArrayList<Triplet<String, WADdeviceType, Integer>> list) {
        this.modBus = modBus;
        this.devicesList = list;
    }

    public HashMap<CharSequence, ModBusAbstractDevice> hashMap() {
        HashMap<CharSequence, ModBusAbstractDevice> map = new HashMap<>();
        for (Triplet<String, WADdeviceType, Integer> item : devicesList) {
            map.put(
                item.getValue0(),
                ModBusAbstractDevice.build(modBus, item.getValue1(), item.getValue2())
            );
        }
        return map;
    }
}
