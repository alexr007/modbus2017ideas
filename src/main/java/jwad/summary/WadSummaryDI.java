package jwad.summary;

import jbus.modbus.InvalidModBusFunction;
import jbus.modbus.response.InvalidModBusResponse;
import jbus.modbus.response.Values;
import jssc.SerialPortException;
import jwad.ModBusAbstractDevice;

import java.util.HashMap;

/**
 * Created by alexr on 27.04.2017.
 */
final public class WadSummaryDI extends WadSummaryBase {
    public WadSummaryDI(ModBusAbstractDevice device) {
        super(device);
    }

    @Override
    protected HashMap<Integer, Object> map () throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        Values ch_fails = device.channel(0).fail();
        Values ch_values = device.channel(0).get();
        return new HashMap<Integer, Object>() {{
            for (int key=1; key<=ch_fails.count(); key++) {
                put(key,new ValuePresentedDI(ch_values.get(key), ch_fails.get(key)));
            }
        }};
    }
}
