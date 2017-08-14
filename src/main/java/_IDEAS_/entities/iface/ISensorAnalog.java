package _IDEAS_.entities.iface;

import j2bus.modbus.InvalidModBusFunction;
import j2bus.modbus.response.InvalidModBusResponse;
import j2bus.modbus.response.Values;
import jssc.SerialPortException;

/**
 * Created by alexr on 07.02.2017.
 *
 */
public interface ISensorAnalog {
    // channel controller failsRaw
    boolean fail() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction;
    // just bytes value 0-65535 range
    Values get() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction;
}
