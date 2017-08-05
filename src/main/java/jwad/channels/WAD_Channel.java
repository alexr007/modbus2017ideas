package jwad.channels;

import jbus.modbus.InvalidModBusFunction;
import jbus.modbus.response.InvalidModBusResponse;
import jbus.modbus.response.Values;
import jbus.modbus.response.ValuesMapped;
import jssc.SerialPortException;
import jwad.chanvalue.ChanValue;
import jwad.modules.WadAbstractDevice;
import jwad.WadDevType;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by alexr on 21.01.2017.
 */
public interface WAD_Channel {
    /**
     * single channel / multiple channels
     * this query make 1 query / response for All Channel types AI, DI, AO, DO
     * only DATA retrieved
     *
     * already implemented in WadAbstractChannel
     * not need to be implement in concrete class WAD-xxx-BUS
     */
    ValuesMapped<ChanValue> get();
    /**
     * single channel / multiple channels
     * this query make 1 query / response for All Channel types AI, DI, AO, DO
     * only DATA retrieved
     *
     * present as abstract in WadAbstractChannel
     * should be implemented in concrete class WAD-xxx-BUS
     */
    Values getRaw();
    /**
     * single channel / multiple channels
     * this query make 2 query / response for DI, DI14, AIK Channel types
     * this query make 1 query / response for DOS, AO, AO6 Channel types
     * retrieved DATA and FAIL status (if available)
     *
     * default implementation here
     * need to be implement only in WAD-DI-BUS, WAD-DI14-BUS
     * where we want to hold Signal+Fail status in one Variable
     */
    default ValuesMapped<ChanValue> getWFails() {
        return get();
    }
    /**
     * single channel / multiple channels
     * this query make 1 query / response for DI, DI14 Channel types
     * all other Channel types just call bytes()
     * only FAIL status retrieved
     */
    default Values fails() throws InvalidModBusFunction, InvalidModBusResponse, SerialPortException {
        throw new InvalidModBusFunction();
    }
    /**
     * single channel only
     * Channel types: DI, DOS
     */
    default boolean opened() throws InvalidModBusFunction, InvalidModBusResponse, SerialPortException {
        throw new InvalidModBusFunction();
    }
    /**
     * single channel only
     * Channel types: DI, DOS
     */
    default boolean closed() throws InvalidModBusFunction, InvalidModBusResponse, SerialPortException {
        throw new InvalidModBusFunction();
    }
    /**
     * single channel only
     * Channel types: DI, AIK
     */
    default boolean failed() throws InvalidModBusFunction, InvalidModBusResponse, SerialPortException {
        throw new InvalidModBusFunction();
    }
    /**
     * single channel only
     * Channel types: DOS
     */
    default void on() throws InvalidModBusFunction, SerialPortException {
        throw new InvalidModBusFunction();
    }
    /**
     * single channel only
     * Channel types: DOS
     */
    default void off() throws InvalidModBusFunction, SerialPortException {
        throw new InvalidModBusFunction();
    }
    /**
     * single channel only
     * Channel types: AO, AO6, DOS
     */
    default void set(int val) throws InvalidModBusFunction, SerialPortException {
        throw new InvalidModBusFunction();
    }
    /**
     * multiple channels: int[]
     * Channel types: AO, AO6, DOS
     */
    default void set(int[] val) throws InvalidModBusFunction, SerialPortException {
        throw new InvalidModBusFunction();
    }
    /**
     * multiple channels: List<ChanValue>
     * Channel types: AO, AO6, DOS
     */
    default void set(List<ChanValue> values) throws InvalidModBusFunction, SerialPortException {
        throw new InvalidModBusFunction();
    }
    /**
     * multiple channels: Stream<ChanValue>
     * Channel types: AO, AO6, DOS
     */
    default void set(Stream<ChanValue> values) throws InvalidModBusFunction, SerialPortException {
        throw new InvalidModBusFunction();
    }
    WadDevType type();
    int channel();
    WadAbstractDevice device();
}
