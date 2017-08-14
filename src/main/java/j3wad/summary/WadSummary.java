package j3wad.summary;

import j2bus.modbus.InvalidModBusFunction;
import j2bus.modbus.response.InvalidModBusResponse;
import jssc.SerialPortException;
import org.xembly.Directives;

/**
 * Created by alexr on 28.04.2017.
 */
public interface WadSummary {
    Directives xmlDir() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction;
    String txt() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction;
}
