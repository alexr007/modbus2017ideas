package jwad.channels;

import jbus.modbus.InvalidModBusFunction;
import jssc.SerialPortException;
import jbase.primitives.Word;
import jbus.modbus.command.MbData;
import jbus.modbus.command.MbMerged;
import jbus.modbus.response.*;
import jwad.chanvalue.ChanValue;
import jwad.chanvalue.IntFromChanValue;
import jwad.modules.WadAbstractDevice;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by alexr on 22.01.2017.
 */
final public class WAD_AO_Channel extends WadAbstractChannel implements WAD_Channel {
    /**
     * @param channel modbus channel id
     *                1..N - mean single channel
     *                0    - mean all channelMap (group operation).
     *                not all functions supports group operation
     * @param device  modbus real device
     */
    public WAD_AO_Channel(int channel, WadAbstractDevice device) {
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

    private Values getMultiple() throws SerialPortException, InvalidModBusResponse {
        return
            new Values.Multiple(
                new WordsFromBytes(
                    new RsAnalyzed(
                        run(builder().cmdReadRegister(0x200C,0x0004)),
                        new RqInfo(device().id(), RsParsed.cmdRead, 8)
                    )
                )
            );
    }

    private Values getSingle() throws SerialPortException, InvalidModBusResponse {
        return
            new Values.Single(
                new WordsFromBytes(
                    new RsAnalyzed(
                        run(builder().cmdReadRegister(0x200C+ chanNumber()-1)),
                        new RqInfo(device().id(), RsParsed.cmdRead, 2)
                    )
                )
            );
    }

    @Override
    public void set(int val) {
        try {
            setUnSafe(val);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    public void setUnSafe(int val) throws SerialPortException {
        assert (chanNumber()>0);
        run(
            builder().cmdWriteRegister(
                0x200C+ chanNumber()-1,
                new MbData(new Word(val))
            )
        );
    }

    @Override
    public void set(List<ChanValue> values) throws InvalidModBusFunction, SerialPortException {
        set(values.stream());
    }

    @Override
    public void set(Stream<ChanValue> values) throws InvalidModBusFunction, SerialPortException {
        set(values.
            mapToInt(value -> new IntFromChanValue(value).get())
            .toArray()
        );
    }

    @Override
    public void set(int[] val) throws SerialPortException {
        assert (chanNumber()==0)&&(val.length==device().properties().chanCount());
        run(
            builder().cmdWriteRegister(
                0x200C+ chanNumber()-1,0x0004,
                new MbMerged(
                    new Word(val[0]).toBytes(),
                    new Word(val[1]).toBytes(),
                    new Word(val[2]).toBytes(),
                    new Word(val[3]).toBytes()
                )
            )
        );
    }
}