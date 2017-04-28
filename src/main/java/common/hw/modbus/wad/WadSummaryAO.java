package common.hw.modbus.wad;

import common.hw.modbus.InvalidModBusFunction;
import common.hw.modbus.response.InvalidModBusResponse;
import common.hw.modbus.response.Values;
import jssc.SerialPortException;

import java.util.HashMap;

/**
 * Created by alexr on 28.04.2017.
 */
final public class WadSummaryAO extends WadSummaryBase {
    public WadSummaryAO(ModBusAbstractDevice device) {
        super(device);
    }

    @Override
    protected HashMap<Integer, String> map () throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        final int I_NOT_INSTALLED = -1;
        final String S_NOT_INSTALLED = "NOT INSTALLED";
        Values ch_values = device.channel(0).get();
        HashMap<Integer, String> map = new HashMap<>();
        for (int key=1; key<=ch_values.count(); key++) {
            int val = ch_values.get(key);
            map.put(key, (val == I_NOT_INSTALLED ? S_NOT_INSTALLED : Integer.toString(val)));
        }
        return map;
    }
}
