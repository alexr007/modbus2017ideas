package jwad.summary;

import jbus.modbus.InvalidModBusFunction;
import jbus.modbus.response.InvalidModBusResponse;
import jssc.SerialPortException;
import jwad.modules.WadAbstractDevice;
import org.xembly.Directives;

import java.util.HashMap;

/**
 * Created by alexr on 28.04.2017.
 */
abstract public class WadSummaryBase implements WadSummary {
    protected final WadAbstractDevice device;

    public WadSummaryBase(WadAbstractDevice device) {
        this.device = device;
    }

    abstract protected HashMap<?, ?> map () throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction;

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
