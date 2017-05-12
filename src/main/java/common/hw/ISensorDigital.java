package common.hw;

import jbus.modbus.InvalidModBusFunction;
import jbus.modbus.response.InvalidModBusResponse;
import jssc.SerialPortException;

/**
 * Created by alexr on 07.02.2017.
 *
 */
public interface ISensorDigital {
    // cable fail
    boolean fail() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction;
    // normal state
    boolean opened() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction;
    // shorted state
    boolean closed() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction;
}
