package jwad.channels;

import jbase.arr.IntBitsFromArray;
import jbase.arr.ArrayFromIntBits;
import jbus.modbus.InvalidModBusFunction;
import jbus.modbus.command.MbData;
import jbus.modbus.response.*;
import jssc.SerialPortException;
import jwad.chanvalue.ChanValue;
import jwad.chanvalue.IntFromChanValue;
import jwad.chanvalue.TypeChan;
import jwad.chanvalue.TypeDO;
import jwad.modules.WadAbstractDevice;

import java.util.List;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;


/**
 * Created by alexr on 21.01.2017.
 */
final public class WAD_DOS_Channel extends WadAbstractChannel implements WAD_Channel {
    /**
     * @param channel modbus channel id
     *                1..N - mean single channel
     *                0    - mean all channelMap (group operation).
     *                not all functions supports group operation
     * @param device  modbus real device
     */
    public WAD_DOS_Channel(int channel, WadAbstractDevice device) {
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
                new ArrayFromIntBits(
                    new RsAnalyzed(
                        device().run(device().builder().cmdReadRegister(0x200B)),
                        new RqInfo(device().id(),RsParsed.cmdRead,2)
                    ).get(1)
                )
            );
    }

    private Values getSingle() throws SerialPortException, InvalidModBusResponse {
        return
            new Values.Single(
                new RsAnalyzed(
                    device().run(device().builder().cmdReadRegister(0x2002+ chanNumber()-1)),
                    new RqInfo(device().id(),RsParsed.cmdRead,2)
                ).get(1)
            );
    }

    @Override
    public Values failsRaw() throws InvalidModBusFunction {
        throw new InvalidModBusFunction();
    }

    @Override
    public boolean opened() throws InvalidModBusResponse, SerialPortException {
        // support only SINGLE port
        assert chanNumber()>0;
        return (getWoFailRaw().get()==0);
    }

    @Override
    public boolean closed() throws InvalidModBusResponse, SerialPortException {
        // support only SINGLE port
        assert chanNumber()>0;
        return (getWoFailRaw().get()==1);
    }

    @Override
    public void on() throws SerialPortException {
        if (chanNumber()==0) {
            onAll();
        } else
            onSingle();
    }

    private void onSingle() throws SerialPortException {
        device().run(
            device().builder().cmdWriteRegister(0x2002+ chanNumber()-1,
                new MbData(new byte[]{0,1})
            )
        );
    }

    @Override
    public void off() throws SerialPortException {
        if (chanNumber()==0) {
            offAll();
        } else
            offSingle();
    }

    private void offSingle() throws SerialPortException {
        device().run(
            device().builder().cmdWriteRegister(0x2002+ chanNumber()-1,
                new MbData(new byte[]{0,0}))
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
        } catch (SerialPortException e) {
            throw new IllegalArgumentException("ModBus Error Happens");
        }
    }

    public void setUnSafe(int val) throws SerialPortException {
        if (chanNumber()==0) {
            setAll(val);
        } else
            setSingle(val);
    }

    private void setSingle(int val) throws SerialPortException {
        run(
            builder().cmdWriteRegister(0x2002+ chanNumber()-1,
                new MbData(new byte[]{0, (byte) (val==0?0:1)})
            )
        );
    }

    private void onAll() throws SerialPortException {
        setAll(0xFF);
    }

    private void offAll() throws SerialPortException {
        setAll(0x00);
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
        // only if channel = 0
        assert (chanNumber()==0);
        if (chanNumber()==0) {
            setAll(val);
        }
    }

    private void setAll(int[] val) throws SerialPortException {
        setAll(new IntBitsFromArray(val).toByte());
    }

    private void setAll(int val) throws SerialPortException {
        device().run(
            device().builder().cmdWriteRegister(0x200B,
                new MbData(new byte[]{0, (byte)val})
            )
        );
    }

}