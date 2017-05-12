package jwad.summary;

import jbus.modbus.InvalidModBusFunction;
import jbus.modbus.response.InvalidModBusResponse;
import jbus.modbus.response.Values;
import jssc.SerialPortException;
import jwad.modules.WadAbstractDevice;

import java.util.HashMap;

/**
 * Created by alexr on 28.04.2017.
 */
final public class WadSummaryAO extends WadSummaryBase {
    public WadSummaryAO(WadAbstractDevice device) {
        super(device);
    }

    @Override
    protected HashMap<Integer, Object> map () throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        Values ch_values = device.channel(0).get();
        return new HashMap<Integer, Object>() {{
            for (int key=1; key<=ch_values.count(); key++) {
                put(key, new ValuePresentedAO(ch_values.get(key)));
            }
        }};
    }
}
