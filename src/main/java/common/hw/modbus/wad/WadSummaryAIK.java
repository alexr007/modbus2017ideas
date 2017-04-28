package common.hw.modbus.wad;

import common.hw.modbus.InvalidModBusFunction;
import common.hw.modbus.response.InvalidModBusResponse;
import common.hw.modbus.response.Values;
import jssc.SerialPortException;
import org.xembly.Directives;

import java.util.HashMap;

/**
 * Created by alexr on 28.04.2017.
 */
final public class WadSummaryAIK  extends WadSummaryBase {
    public WadSummaryAIK(ModBusAbstractDevice device) {
        super(device);
    }

    @Override
    protected HashMap<Integer, String> map () throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        Values ch_values = device.channel(0).get();
        HashMap<Integer, String> map = new HashMap<>();
        for (int key=1; key<=ch_values.count(); key++) {
            int val = ch_values.get(key);
            map.put(key, Integer.toString(val));
        }
        return map;
    }
}
