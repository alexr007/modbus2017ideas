package j3wad.channels;

import j1base.arr.ArrayFromIntBits;
import j2bus.modbus.response.*;
import jssc.SerialPortException;
import j3wad.modules.WadAbstractDevice;

import java.io.IOException;
import java.util.stream.IntStream;

/**
 * Created by alexr on 06.02.2017.
 */
final public class WAD_DI14_Channel extends WadAbstractChannel implements WAD_Channel {
    /**
     * @param channel modbus channel id
     *                1..N - mean single channel
     *                0    - mean all channelMap (group operation).
     *                not all functions supports group operation
     * @param device  modbus real device
     */
    public WAD_DI14_Channel(int channel, WadAbstractDevice device) {
        super(channel, device);
    }

    @Override
    public Values getWoFailRaw()  {
        try {
            return chanNumber()==0
                ? getMultiple()
                : getSingle();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Values getMultiple() {
        return
            new Values.Multiple(
                new ArrayFromIntBits(
                    new WordsFrom2Bytes(device().read_(0x1FFE)).get0(),
                    15
                )
            );
    }

    /*
    * returns:
    * 1 - channel is SHORT
    * 0 - channel is OPEN
    * 2 - channel is FAIL
    */
    private Values getSingle() {
        return
            new Values.Single(
                device().read_(0x2001+ chanNumber()-1).get(1)
            );
    }

    /**
     * this implementation mix Values + Fails
     * @return
     */
    @Override
    public Values getRaw() {
        Values ch_fails = failsRaw();
        Values ch_values = getWoFailRaw();
        return new Values.Multiple(
            IntStream.range(1, ch_fails.count() + 1).boxed()
                .mapToInt(index -> ch_values.get(index) + 2 * ch_fails.get(index))
                .toArray()
        );
    }

    @Override
    /*
     * 1FFF
     * Состояние «Обрыв лини» по всем каналам
     * 1 - обрыв
     * 0 - все ОК
     *
     */
    public Values failsRaw() {
        try {
            return chanNumber()==0
                ? failMultiple()
                : failSingle();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    private Values failMultiple() {
        return
            new Values.Multiple(
                new ArrayFromIntBits(failAll(),15)
            );
    }

    private Values failSingle() {
        return
            new Values.Single(
                (failAll()>>(chanNumber()-1))&0b1
            );
    }

    private int failAll() {
        return
            new WordsFrom2Bytes(device().read_(0x1FFF)).get0();
    }

    @Override
    public boolean opened() {
        return chanNumber()==0
            ? (getMultiple().get()&0b0111111111111111)==0x0
            : getSingle().get()==0;
    }

    @Override
    public boolean closed() {
        return chanNumber()==0
            ? (getMultiple().get()&0b0111111111111111)==0b0111111111111111
            : getSingle().get()==1;
    }
}