package app.persistence.init;

import constants.DevName;
import jbus.modbus.ModBus;
import jwad.modules.WadAbstractDevice;
import jwad.WadDevType;
import org.javatuples.Triplet;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by alexr on 26.04.2017.
 */
public class DevicesFromList implements DevicesFrom {
    private final ModBus modBus;
    private final ArrayList<Triplet<DevName, WadDevType, Integer>> devicesList;

    public DevicesFromList(ModBus modBus, ArrayList<Triplet<DevName, WadDevType, Integer>> list) {
        this.modBus = modBus;
        this.devicesList = list;
    }

    @Override
    public HashMap<DevName, WadAbstractDevice> hashMap() throws Exception {
        HashMap<DevName, WadAbstractDevice> map = new HashMap<>();
        for (Triplet<DevName, WadDevType, Integer> item : devicesList) {
            if (map.containsKey(item.getValue0())) {
                throw new Exception(String.format("Duplicate Device name: %s", item.getValue0()));
            }
            map.put(
                item.getValue0(),
                WadAbstractDevice.build(modBus, item.getValue1(), item.getValue2())
            );
        }
        return map;
    }
}
