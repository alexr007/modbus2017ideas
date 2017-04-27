package common.hw.modbus.wad;

import common.hw.modbus.InvalidModBusFunction;
import common.hw.modbus.response.InvalidModBusResponse;
import common.hw.modbus.response.Values;
import common.sw.persistence.TypeDI;
import jssc.SerialPortException;
import org.xembly.Directives;

import java.util.HashMap;
import java.util.stream.Stream;

/**
 * Created by alexr on 27.04.2017.
 */
public class WAD_DI_Summary {
    private final ModBusAbstractDevice device;

    public WAD_DI_Summary(ModBusAbstractDevice device) {
        this.device = device;
    }

    private HashMap<Integer, TypeDI> map () throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
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

    public Directives xmlDir() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        Directives dirs = new Directives();
        map().forEach((k, v) ->
            dirs.add("channel")
                .add("id").set(k).up()
                .add("value").set(v.toString()).up()
                .up()
        );
        return dirs;
    }

    public String txt() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        StringBuilder sb = new StringBuilder("details:\n");
        map().forEach((k, v) ->
            sb.append(String.format("Channel %2d: %s\n", k, v.toString()))
        );
        return sb.toString();
    }

}
