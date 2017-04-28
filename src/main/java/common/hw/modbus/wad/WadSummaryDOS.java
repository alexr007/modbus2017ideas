package common.hw.modbus.wad;

import common.hw.modbus.InvalidModBusFunction;
import common.hw.modbus.response.InvalidModBusResponse;
import common.hw.modbus.response.Values;
import common.sw.persistence.TypeDO;
import jssc.SerialPortException;

import java.util.HashMap;

/**
 * Created by alexr on 27.04.2017.
 */
final public class WadSummaryDOS extends WadSummaryBase{
    public WadSummaryDOS(ModBusAbstractDevice device) {
        super(device);
    }

    @Override
    protected HashMap<Integer, TypeDO> map () throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        Values ch_values = device.channel(0).get();
        HashMap<Integer, TypeDO> map = new HashMap<>();
        for (int key=1; key<=ch_values.count(); key++) {
            map.put(key,
                ch_values.get(key)==1 ? TypeDO.ON : TypeDO.OFF);
        }
        return map;
    }


}
