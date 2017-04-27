package common.sw.layers;

import common.hw.modbus.ModBus;
import common.hw.modbus.wad.ModBusAbstractDevice;
import common.hw.modbus.wad.WadDevType;
import org.javatuples.Triplet;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by alexr on 26.04.2017.
 */
public class DevicesFromList {
    private final ModBus modBus;
    private final ArrayList<Triplet<String, WadDevType, Integer>> devicesList;

    public DevicesFromList(ModBus modBus, ArrayList<Triplet<String, WadDevType, Integer>> list) {
        this.modBus = modBus;
        this.devicesList = list;
    }

    public HashMap<CharSequence, ModBusAbstractDevice> hashMap() throws Exception {
        HashMap<CharSequence, ModBusAbstractDevice> map = new HashMap<>();
        for (Triplet<String, WadDevType, Integer> item : devicesList) {
            if (map.containsKey(item.getValue0())) {
                throw new Exception(String.format("Duplicate Device name: %s", item.getValue0()));
            }
            map.put(
                item.getValue0(),
                ModBusAbstractDevice.build(modBus, item.getValue1(), item.getValue2())
            );
        }
        return map;
    }
}
