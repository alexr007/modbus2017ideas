package common.hw;

import common.hw.modbus.wad.ModBusInvalidFunction;
import jssc.SerialPortException;

/**
 * Created by alexr on 07.02.2017.
 *
 */
public interface Switch {
    void on() throws SerialPortException, ModBusInvalidFunction;
    void off() throws SerialPortException, ModBusInvalidFunction;
}
