package j3wad.channels;

import j2bus.modbus.response.*;
import jssc.SerialPortException;
import j3wad.chanvalue.ChanValue;
import j3wad.chanvalue.IntFromChanValue;
import j3wad.modules.WadAbstractDevice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

abstract class WAD_AO_ extends WadAbstractChannel implements WAD_Channel {
    /**
     * @param channel modbus channel id
     *                1..N - mean single channel
     *                0    - mean all channelMap (group operation).
     *                not all functions supports group operation
     * @param device  modbus real device
     */
    public WAD_AO_(int channel, WadAbstractDevice device) {
        super(channel, device);
    }

    @Override
    public Values getWoFailRaw() {
        try {
            return chanNumber()==0
                ? getMultiple()
                : getSingle();
        } catch (SerialPortException e) {
            throw new IllegalArgumentException(e);
        } catch (InvalidModBusResponse e) {
            throw new IllegalArgumentException(e);
        }
    }

    protected abstract Values getMultiple() throws SerialPortException, InvalidModBusResponse;

    protected abstract Values getSingle() throws SerialPortException, InvalidModBusResponse;

    @Override
    public void set(ChanValue val) {
        set(new IntFromChanValue(val));
    }

    @Override
    public void set(IntFromChanValue val) {
        set(val.get());
    }

    @Override
    public void set(int val) {
        try {
            setUnSafe(val);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    public abstract void setUnSafe(int val) throws SerialPortException;

    @Override
    public void set(Map<Integer, ChanValue> values) {
        set(values.entrySet().stream()
            .sorted((o1, o2) -> o1.getKey().compareTo(o2.getKey()))
            .map(entry -> entry.getValue())
            .collect(Collectors.toList())
        );
    }

    @Override
    public void set(List<ChanValue> values) {
        set(values.stream());
    }

    @Override
    public void set(Stream<ChanValue> values) {
        set(values.
            mapToInt(value -> new IntFromChanValue(value).get())
            .toArray()
        );
    }

    @Override
    public abstract void set(int[] val);

}
