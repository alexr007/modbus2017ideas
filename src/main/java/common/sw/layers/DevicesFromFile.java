package common.sw.layers;

import jbus.modbus.ModBus;
import jwad.ModBusAbstractDevice;

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

    public HashMap<CharSequence, ModBusAbstractDevice> hashMap() throws Exception {
        HashMap<CharSequence, ModBusAbstractDevice> map = new HashMap<>();
        if (true) throw new Exception("not implemented");
/*
        for (Triplet<String, WadDevType, Integer> item : devicesList) {
            map.put(
                item.getValue0(),
                ModBusAbstractDevice.build(modBus, item.getValue1(), item.getValue2())
            );
        }
*/
        return map;
    }

}
