package jwad.summary;

import jbus.modbus.InvalidModBusFunction;
import jbus.modbus.response.InvalidModBusResponse;
import jbus.modbus.response.Values;
import jssc.SerialPortException;
import jwad.modules.WadAbstractDevice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by alexr on 27.04.2017.
 */
final public class WadSummaryDI extends WadSummaryBase {
    public WadSummaryDI(WadAbstractDevice device) {
        super(device);
    }

    //@Override
    protected Map<Integer, ValuePresented> map_OLD() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        Values ch_fails = device.channel(0).fail();
        Values ch_values = device.channel(0).get();
        return new HashMap<Integer, ValuePresented>() {{
            for (int key=1; key<=ch_fails.count(); key++) {
                put(key,new ValuePresentedDI(ch_values.get(key), ch_fails.get(key)));
            }
        }};
    }

    @Override
    protected Map<Integer, ValuePresented> map() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        Values ch_fails = device.channel(0).fail();
        Values ch_values = device.channel(0).get();
        return IntStream.range(1, ch_fails.count() + 1).boxed()
            .collect(Collectors.toMap(
                key -> key, key -> new ValuePresentedDI(ch_values.get(key), ch_fails.get(key))
            ));
    }
}
