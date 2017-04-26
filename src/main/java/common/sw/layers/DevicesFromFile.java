package common.sw.layers;

import common.hw.modbus.ModBus;
import common.hw.modbus.wad.ModBusAbstractDevice;
import common.hw.modbus.wad.WADdeviceType;
import org.javatuples.Triplet;

import java.io.File;
import java.util.HashMap;

/**
 * Created by alexr on 26.04.2017.
 */
public class DevicesFromFile {
    private final ModBus modBus;
    private final File origin;

    public DevicesFromFile(ModBus modBus, File origin) {
        this.modBus = modBus;
        this.origin = origin;
    }

    public HashMap<String, ModBusAbstractDevice> hashMap() throws Exception {
        HashMap<String, ModBusAbstractDevice> map = new HashMap<>();
        if (true) throw new Exception("not implemented");
/*
        for (Triplet<String, WADdeviceType, Integer> item : devicesList) {
            map.put(
                item.getValue0(),
                ModBusAbstractDevice.build(modBus, item.getValue1(), item.getValue2())
            );
        }
*/
        return map;
    }

}
