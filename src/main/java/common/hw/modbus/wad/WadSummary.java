package common.hw.modbus.wad;

import common.hw.modbus.InvalidModBusFunction;
import common.hw.modbus.response.InvalidModBusResponse;
import jssc.SerialPortException;
import org.xembly.Directives;

/**
 * Created by alexr on 28.04.2017.
 */
public interface WadSummary {
    Directives xmlDir() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction;
    String txt() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction;
}
