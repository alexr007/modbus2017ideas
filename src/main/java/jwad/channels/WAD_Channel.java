package jwad.channels;

import jbus.modbus.InvalidModBusFunction;
import jbus.modbus.response.InvalidModBusResponse;
import jbus.modbus.response.Values;
import jssc.SerialPortException;
import jwad.modules.WadAbstractDevice;
import jwad.WadDevType;

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
    // AO, AO6, DOS
    default void set(int val) throws InvalidModBusFunction, SerialPortException {
        throw new InvalidModBusFunction();
    }
    default void set(int[] val) throws InvalidModBusFunction, SerialPortException {
        throw new InvalidModBusFunction();
    }
    WadDevType type();
    int channel();
    default WadAbstractDevice device() throws InvalidModBusFunction {
        // TODO need to implement for use magic when grouping ModBus Requests
        throw new InvalidModBusFunction();
    }
}
