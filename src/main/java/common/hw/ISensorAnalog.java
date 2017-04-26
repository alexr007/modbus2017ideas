package common.hw;

import common.hw.modbus.InvalidModBusFunction;
import common.hw.modbus.response.InvalidModBusResponse;
import common.hw.modbus.response.Values;
import jssc.SerialPortException;

/**
 * Created by alexr on 07.02.2017.
 *
 */
public interface ISensorAnalog {
    // channel controller fail
    boolean fail() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction;
    // just get value 0-65535 range
    Values get() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction;
}
