package common.hw;

import common.hw.modbus.InvalidModBusFunction;
import common.hw.modbus.response.InvalidModBusResponse;
import jssc.SerialPortException;

/**
 * Created by alexr on 07.02.2017.
 *
 */
public interface ISensorDigital {
    // cable fail
    boolean fail() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction;
    // normal state
    boolean opened() throws InvalidModBusResponse, SerialPortException;
    // shorted state
    boolean closed() throws InvalidModBusResponse, SerialPortException;
}
