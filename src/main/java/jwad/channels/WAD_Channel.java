package jwad.channels;

import jbus.modbus.InvalidModBusFunction;
import jbus.modbus.response.InvalidModBusResponse;
import jbus.modbus.response.Values;
import jbus.modbus.response.ValuesMapped;
import jssc.SerialPortException;
import jwad.chanvalue.ChanValue;
import jwad.chanvalue.IntFromChanValue;
import jwad.modules.WadAbstractDevice;
import jwad.WadDevType;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by alexr on 21.01.2017.
 * Interface to represent WAD_BUS Device channel
 */
public interface WAD_Channel {
    String ERROR = "Not implemented";
    /**
     * single channel / multiple channels
     * Values mapped to ValuesMapped<ChanValue>
     * retrieved Data + Fail Signal (if available) in one variable
     * runs 2 query / response for DI, DI14 Channel types (see Values getRaw())
     * runs 1 query / response for AIK, DOS, AO, AO6 Channel types
     * already implemented in WadAbstractChannel
     */
    ValuesMapped<ChanValue> get();
    /**
     * single channel / multiple channels
     * by default call getWoFailRaw()
     * need to implement if channel supports Fail Signal (DI, DI14)
     */
    Values getRaw();
    /**
     * single channel / multiple channels
     * Values mapped to ValuesMapped<ChanValue>
     * retrieved Data ONLY (w/o Fail Signal)
     * runs 1 query / response for all channel types
     * already implemented in WadAbstractChannel
     */
    ValuesMapped<ChanValue> getWoFail();
    /**
     * single channel / multiple channels
     * must implement for any type concrete channel
     */
    Values getWoFailRaw();
    /**
     * single channel / multiple channels
     * runs 1 query / response for DI, DI14 channel types
     * only FAIL status retrieved
     */
    default Values failsRaw() throws InvalidModBusFunction, InvalidModBusResponse, SerialPortException {
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
     * single / multiple (?) channels
     * Channel types: DOS
     */
    default void on() throws InvalidModBusFunction, SerialPortException {
        throw new InvalidModBusFunction();
    }
    /**
     * single / multiple (?) channels
     * Channel types: DOS
     */
    default void off() throws InvalidModBusFunction, SerialPortException {
        throw new InvalidModBusFunction();
    }
    /**
     * single channel only
     * Channel types: AO, AO6, DOS
     */
    default void set(ChanValue val) {
        throw new IllegalArgumentException(ERROR);
    }
    default void set(IntFromChanValue val) {
        throw new IllegalArgumentException(ERROR);
    }
    default void set(int val) {
        throw new IllegalArgumentException(ERROR);
    }
    /**
     * multiple channels: iface[]
     * Channel types: AO, AO6, DOS
     */
    default void set(int[] val) {
        throw new IllegalArgumentException(ERROR);
    }
    /**
     * multiple channels: List<ChanValue>
     * Channel types: AO, AO6, DOS
     */
    default void set(List<ChanValue> values) {
        throw new IllegalArgumentException(ERROR);
    }
    /**
     * multiple channels: Stream<ChanValue>
     * Channel types: AO, AO6, DOS
     */
    default void set(Stream<ChanValue> values) {
        throw new IllegalArgumentException(ERROR);
    }
    default void set(Map<Integer, ChanValue> values) { throw new IllegalArgumentException(ERROR);}
    /**
     * @return device type :AIK, AO, AO6, DI, DI14, DOS
     */
    WadDevType type();
    /**
     * @return channel number integer 1..chanCount
     */
    int chanNumber();
    /**
     * @return link to parent device
     */
    WadAbstractDevice device();
}
