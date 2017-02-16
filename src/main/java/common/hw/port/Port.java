package common.hw.port;

import common.hw.modbus.response.InvalidModBusResponse;
import common.hw.modbus.response.Values;
import common.hw.modbus.wad.ModBusInvalidFunction;
import jssc.SerialPortException;

/**
 * Created by alexr on 07.02.2017.
 */
interface Port {
    Values get() throws InvalidModBusResponse, SerialPortException, ModBusInvalidFunction;
    void set (int value) throws Exception;
    void on() throws Exception;
    void off() throws Exception;
}
