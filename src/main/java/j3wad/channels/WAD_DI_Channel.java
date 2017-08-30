package j3wad.channels;

import j1base.arr.ArrayFromIntBits;
import j2bus.modbus.response.*;
import jssc.SerialPortException;
import j3wad.modules.WadAbstractDevice;

import java.io.IOException;
import java.util.stream.IntStream;

/**
 * Created by alexr on 22.01.2017.
 */
final public class WAD_DI_Channel extends WadAbstractChannel implements WAD_Channel {
    /**
     * @param channel modbus channel id
     *                1..N - mean single channel
     *                0    - mean all channelMap (group operation).
     *                not all functions supports group operation
     * @param device  modbus real device
     */
    public WAD_DI_Channel(int channel, WadAbstractDevice device) {
        super(channel, device);
    }

    @Override
    public Values getWoFailRaw() {
        return chanNumber()==0
            ? getMultiple()
            : getSingle();
    }

    private Values getMultiple()  {
        try {
            return new Values.Multiple(
                    new ArrayFromIntBits( device().read_(0x200D).get(1),
                        8
                    )
                );
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /*
    * returns:
    * 1 - channel is SHORT
    * 0 - channel is OPEN
    * 2 - channel is FAIL
    */
    private Values getSingle() {
        try {
            return
                new Values.Single(
                    device().read_(0x2004+ chanNumber()-1).get(1)
                );
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * this implementation mix Values + Fails
     * @return
     */
    @Override
    public Values getRaw() {
        if (chanNumber()==0) {
            // multiple
            Values ch_fails = failsRaw();
            Values ch_values = getWoFailRaw();
            return new Values.Multiple(
                IntStream.range(1, ch_fails.count() + 1).boxed()
                    .mapToInt(index -> ch_values.get(index) + 2 * ch_fails.get(index))
                    .toArray()
            );
        } else {
            // single
            return getSingle();
        }
    }

    @Override
    /*
     * 200E
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
            throw new IllegalArgumentException("ModBusError");
        }
    }

    private Values failMultiple() {
        return new Values.Multiple(
            new ArrayFromIntBits(failAll(),8)
        );
    }

    private Values failSingle() {
        return new Values.Single(
            (failAll()>>(chanNumber()-1))&0b1
        );
    }

    private int failAll() {
        return device().read_(0x200E).get(1);
    }

    @Override
    public boolean opened() {
        return chanNumber()==0
        ? (getMultiple().get()&0b11111111)==0x0
        : getSingle().get()==0;
    }

    @Override
    public boolean closed() {
        return chanNumber()==0
            ? (getMultiple().get()&0b11111111)==0b11111111
            : getSingle().get()==1;
    }
}