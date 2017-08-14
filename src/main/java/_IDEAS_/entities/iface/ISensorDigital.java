package _IDEAS_.entities.iface;

import j2bus.modbus.InvalidModBusFunction;
import j2bus.modbus.response.InvalidModBusResponse;
import jssc.SerialPortException;

/**
 * Created by alexr on 07.02.2017.
 *
 */
public interface ISensorDigital {
    // cable failsRaw
    boolean fail() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction;
    // normal state
    boolean opened() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction;
    // shorted state
    boolean closed() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction;
}
