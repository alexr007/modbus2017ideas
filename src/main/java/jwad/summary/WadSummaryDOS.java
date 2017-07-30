package jwad.summary;

import jbus.modbus.InvalidModBusFunction;
import jbus.modbus.response.InvalidModBusResponse;
import jbus.modbus.response.Values;
import jssc.SerialPortException;
import jwad.modules.WadAbstractDevice;

import java.util.HashMap;
import java.util.Map;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by alexr on 27.04.2017.
 */
final public class WadSummaryDOS extends WadSummaryBase{
    public WadSummaryDOS(WadAbstractDevice device) {
        super(device);
    }

    //@Override
    protected Map<Integer, ValuePresented> map_OLD() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        Values ch_values = device.channel(0).get();
        return new HashMap<Integer, ValuePresented>(){{
            for (int key=1; key<=ch_values.count(); key++) {
                put(key, new ValuePresentedDOS(ch_values.get(key)));
            }
        }};
    }

    @Override
    protected Map<Integer, ValuePresented> map() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        Values ch_values = device.channel(0).get();
        return IntStream.range(1, ch_values.count() + 1).boxed()
            .collect(Collectors.toMap(
                key -> key, key -> new ValuePresentedDOS(ch_values.get(key))
            ));
    }

}
