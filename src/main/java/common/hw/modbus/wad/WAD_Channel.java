package common.hw.modbus.wad;

import common.hw.modbus.response.InvalidModBusResponse;
import common.hw.modbus.response.Values;
import jssc.SerialPortException;

/**
 * Created by alexr on 21.01.2017.
 */
public interface WAD_Channel {
    // AI, DI, DO, DOS
    // Values.Single
    // Values.Multiple
    Values get() throws SerialPortException, InvalidModBusResponse, ModBusInvalidFunction;
    // DI, AI
    Values fail() throws ModBusInvalidFunction, InvalidModBusResponse, SerialPortException;
    // DI, DOS
    // TODO doubtful if channel==0
    boolean opened() throws InvalidModBusResponse, SerialPortException, ModBusInvalidFunction;
    boolean closed() throws InvalidModBusResponse, SerialPortException, ModBusInvalidFunction;
    // DOS
    void on() throws SerialPortException, ModBusInvalidFunction;
    void off() throws SerialPortException, ModBusInvalidFunction;
    // AO, DOS
    void set(int val) throws SerialPortException, ModBusInvalidFunction;
    void set(int[] val) throws SerialPortException, ModBusInvalidFunction;
}
