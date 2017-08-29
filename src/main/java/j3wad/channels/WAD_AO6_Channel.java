package j3wad.channels;

import j2bus.modbus.command.MbData;
import j2bus.modbus.command.MbMerged;
import j2bus.modbus.response.*;
import j1base.primitives.Word;
import jssc.SerialPortException;
import j3wad.chanvalue.ChanValue;
import j3wad.chanvalue.IntFromChanValue;
import j3wad.modules.WadAbstractDevice;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by alexr on 22.01.2017.
 */
final public class WAD_AO6_Channel extends WadAbstractChannel implements WAD_Channel {
    /**
     * @param channel modbus channel id
     *                1..N - mean single channel
     *                0    - mean all channelMap (group operation).
     *                not all functions supports group operation
     * @param device  modbus real device
     */
    public WAD_AO6_Channel(int channel, WadAbstractDevice device) {
        super(channel, device);
    }

    @Override
    public Values getWoFailRaw() {
        try {
            return chanNumber()==0
                ? getMultiple()
                : getSingle();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Values getMultiple() throws IOException {
        return
            new Values.Multiple(
                new WordsFrom2Bytes( device().read_(0x2010,0x0006))
            );
    }

    private Values getSingle() throws IOException {
        return
            new Values.Single(
                new WordsFrom2Bytes( device().read_(0x2010+ chanNumber()-1))
            );
    }

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
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void setUnSafe(int val) throws IOException {
        assert (chanNumber()>0);
        device().write_(0x2010 + chanNumber()-1, new MbData(new Word(val)));
    }

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
    public void set(int[] val) {
        checkForGroupWrite(val.length);
        try {
            device().write_(0x2010+ chanNumber(),0x0006,
                new MbMerged(
                    new Word(val[0]).toBytes(),
                    new Word(val[1]).toBytes(),
                    new Word(val[2]).toBytes(),
                    new Word(val[3]).toBytes(),
                    new Word(val[4]).toBytes(),
                    new Word(val[5]).toBytes()
                ));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}