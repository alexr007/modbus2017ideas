package common.hw.modbus.wad;

import common.hw.modbus.InvalidModBusFunction;
import common.hw.modbus.response.InvalidModBusResponse;
import common.hw.modbus.response.Values;
import common.sw.persistence.TypeDI;
import jssc.SerialPortException;
import java.util.HashMap;

/**
 * Created by alexr on 27.04.2017.
 */
final public class WadSummaryDI extends WadSummaryBase {
    public WadSummaryDI(ModBusAbstractDevice device) {
        super(device);
    }

    @Override
    protected HashMap<Integer, TypeDI> map () throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        Values ch_fails = device.channel(0).fail();
        Values ch_values = device.channel(0).get();
        TypeDI value;
        HashMap<Integer, TypeDI> map = new HashMap<>();
        for (int key=1; key<=ch_fails.count(); key++) {
            value = ch_fails.get(key)==1 ? TypeDI.FAIL :
                (ch_values.get(key)==1 ? TypeDI.CLOSED : TypeDI.OPENED);
            map.put(key,value);
        }
        return map;
    }
}
