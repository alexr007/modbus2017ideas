package jwad.channels;

import jbase.arr.ArrayFromIntBits;
import jbus.modbus.InvalidModBusFunction;
import jbus.modbus.response.*;
import jssc.SerialPortException;
import jwad.chanvalue.ChanValue;
import jwad.modules.WadAbstractDevice;
import jwad.summary.ValuePresentedDI;

import java.util.Map;
import java.util.stream.Collectors;
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
    public Values getRaw()  {
        try {
            return channel()==0
                ? getMultiple()
                : getSingle();
        } catch (SerialPortException e) {
            throw new IllegalArgumentException(e);
        } catch (InvalidModBusResponse e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Values getMultiple() throws SerialPortException, InvalidModBusResponse {
/*
        int[] data = new RsAnalyzed(
                run(builder().cmdReadRegister(0x1FFE)),
                new RqInfo(device().id(), RsParsed.cmdRead, 2)
        ).get();
//                    (data[0] & 0xFF) << 8 | data[1] & 0xFF,
*/
        return
            new Values.Multiple(
                new ArrayFromIntBits(
                    new WordsFromBytes(
                        new RsAnalyzed(
                            run(builder().cmdReadRegister(0x1FFE)),
                            new RqInfo(device().id(), RsParsed.cmdRead, 2)
                        )).get0(),
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
    private Values getSingle() throws SerialPortException, InvalidModBusResponse {
        return
            new Values.Single(
                new RsAnalyzed(
                    run(builder().cmdReadRegister(0x2001+channel()-1)),
                    new RqInfo(device().id(), RsParsed.cmdRead, 2)
                ).get(1)
            );
    }

    @Override
    public Values getWFailsRaw() {
        Values ch_fails = fails();
        Values ch_values = getRaw();
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
    public Values fails() {
        try {
            return channel()==0
                ? failMultiple()
                : failSingle();
        } catch (InvalidModBusResponse e) {
            throw new IllegalArgumentException("ModBusError");
        } catch (SerialPortException e) {
            throw new IllegalArgumentException("ModBusError");
        }
    }

    private Values failMultiple() throws InvalidModBusResponse, SerialPortException {
        return
            new Values.Multiple(
                new ArrayFromIntBits(failAll(),15)
            );
    }

    private Values failSingle() throws InvalidModBusResponse, SerialPortException {
        return
            new Values.Single(
                (failAll()>>(channel()-1))&0b1
            );
    }

    private int failAll() throws SerialPortException, InvalidModBusResponse {
        return
            new WordsFromBytes(new RsAnalyzed(
                run(builder().cmdReadRegister(0x1FFF)),
                new RqInfo(device().id(), RsParsed.cmdRead, 2)
            )).get0();
    }

    @Override
    public boolean opened() throws InvalidModBusFunction, InvalidModBusResponse, SerialPortException {
        return channel()==0
            ? (getMultiple().get()&0b0111111111111111)==0x0
            : getSingle().get()==0;
    }

    @Override
    public boolean closed() throws InvalidModBusFunction, InvalidModBusResponse, SerialPortException {
        return channel()==0
            ? (getMultiple().get()&0b0111111111111111)==0b0111111111111111
            : getSingle().get()==1;
    }
}