package common.hw;

import common.hw.modbus.response.InvalidModBusResponse;
import common.hw.modbus.wad.ModBusInvalidFunction;
import jssc.SerialPortException;

/**
 * Created by alexr on 07.02.2017.
 *
 */
public interface ISensorDigital {
    // cable fail
    boolean fail() throws InvalidModBusResponse, ModBusInvalidFunction, SerialPortException;
    // normal state
    boolean opened() throws InvalidModBusResponse, SerialPortException, ModBusInvalidFunction;
    // shorted state
    boolean closed() throws InvalidModBusResponse, SerialPortException, ModBusInvalidFunction;
}
