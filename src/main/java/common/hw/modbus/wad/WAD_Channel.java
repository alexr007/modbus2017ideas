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
    default Values get() throws InvalidModBusFunction, SerialPortException, InvalidModBusResponse {
        throw new InvalidModBusFunction();
    }
    // DI, AI
    default Values fail() throws InvalidModBusFunction, InvalidModBusResponse, SerialPortException {
        throw new InvalidModBusFunction();
    }
    // DI, DOS
    // TODO doubtful if channel==0
    default boolean opened() throws InvalidModBusFunction, InvalidModBusResponse, SerialPortException {
        throw new InvalidModBusFunction();
    }
    default boolean closed() throws InvalidModBusFunction, InvalidModBusResponse, SerialPortException {
        throw new InvalidModBusFunction();
    }
    // DOS
    default void on() throws InvalidModBusFunction, SerialPortException {
        throw new InvalidModBusFunction();
    }
    default void off() throws InvalidModBusFunction, SerialPortException {
        throw new InvalidModBusFunction();
    }
    // AO, DOS
    default void set(int val) throws InvalidModBusFunction, SerialPortException {
        throw new InvalidModBusFunction();
    }
    default void set(int[] val) throws InvalidModBusFunction, SerialPortException {
        throw new InvalidModBusFunction();
    }
}
