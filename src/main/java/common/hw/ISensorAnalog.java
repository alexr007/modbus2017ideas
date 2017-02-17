package common.hw;

import common.hw.modbus.response.InvalidModBusResponse;
import common.hw.modbus.response.Values;
import common.hw.modbus.wad.ModBusInvalidFunction;
import jssc.SerialPortException;

/**
 * Created by alexr on 07.02.2017.
 *
 */
public interface ISensorAnalog {
    // channel controller fail
    boolean fail() throws InvalidModBusResponse, ModBusInvalidFunction, SerialPortException;
    // just get value 0-65535 range
    Values get() throws InvalidModBusResponse, SerialPortException, ModBusInvalidFunction;
}
