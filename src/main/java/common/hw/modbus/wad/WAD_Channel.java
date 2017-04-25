package common.hw.modbus.wad;

import common.hw.modbus.InvalidModBusFunction;
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
    Values get() throws SerialPortException, InvalidModBusResponse;
    // DI, AI
    Values fail() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction;
    // DI, DOS
    // TODO doubtful if channel==0
    boolean opened() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction;
    boolean closed() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction;
    // DOS
    void on() throws SerialPortException, InvalidModBusFunction;
    void off() throws SerialPortException, InvalidModBusFunction;
    // AO, DOS
    void set(int val) throws SerialPortException, InvalidModBusFunction;
    void set(int[] val) throws SerialPortException, InvalidModBusFunction;
}
