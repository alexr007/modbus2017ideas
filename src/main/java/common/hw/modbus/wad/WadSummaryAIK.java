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
    protected HashMap<Integer, Object> map () throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        Values ch_values = device.channel(0).get();
        return new HashMap<Integer, Object>() {{
            for (int key=1; key<=ch_values.count(); key++) {
                put(key, new ValuePresentedAIK(ch_values.get(key)));
            }
        }};
    }
}
