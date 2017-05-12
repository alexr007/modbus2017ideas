package jwad.channels;

import common.sw.common.ArrayToInt;
import common.sw.common.IntToArray;
import jbus.modbus.InvalidModBusFunction;
import jbus.modbus.command.MbData;
import jbus.modbus.response.*;
import jssc.SerialPortException;
import jwad.modules.WadAbstractDevice;
import jwad.WadDevType;


/**
 * Created by alexr on 21.01.2017.
 */
final public class WAD_DOS_Channel extends WadAbstractChannel implements WAD_Channel {
    /**
     * @param channel modbus channel id
     *                1..N - mean single channel
     *                0    - mean all channels (group operation).
     *                not all functions supports group operation
     * @param device  modbus real device
     */
    public WAD_DOS_Channel(int channel, WadAbstractDevice device) {
        super(channel, device);
    }

    @Override
    public Values get() throws SerialPortException, InvalidModBusResponse {
        if (channel()==0) {
            return getMultiple();
        } else
            return getSingle();
    }

    private Values getMultiple() throws SerialPortException, InvalidModBusResponse {
        return
            new Values.Multiple(
                new IntToArray(
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
                    device().run(device().builder().cmdReadRegister(0x2002+channel()-1)),
                    new RqInfo(device().id(),RsParsed.cmdRead,2)
                ).get(1)
            );
    }

    @Override
    public Values fail() throws InvalidModBusFunction {
        throw new InvalidModBusFunction();
    }

    @Override
    public boolean opened() throws InvalidModBusResponse, SerialPortException {
        // support only SINGLE port
        assert channel()>0;
        return (get().get()==0);
    }

    @Override
    public boolean closed() throws InvalidModBusResponse, SerialPortException {
        // support only SINGLE port
        assert channel()>0;
        return (get().get()==1);
    }

    @Override
    public void on() throws SerialPortException {
        if (channel()==0) {
            onAll();
        } else
            onSingle();
    }

    private void onSingle() throws SerialPortException {
        device().run(
            device().builder().cmdWriteRegister(0x2002+channel()-1,
                new MbData(new byte[]{0,1})
            )
        );
    }

    private void onAll() throws SerialPortException {
        setAll(0xFF);
    }

    @Override
    public void off() throws SerialPortException {
        if (channel()==0) {
            offAll();
        } else
            offSingle();
    }

    private void offSingle() throws SerialPortException {
        device().run(
            device().builder().cmdWriteRegister(0x2002+channel()-1,
                new MbData(new byte[]{0,0}))
        );
    }

    private void offAll() throws SerialPortException {
        setAll(0x00);
    }

    @Override
    public void set(int val) throws SerialPortException {
        if (channel()==0) {
            setAll(val);
        } else
            setSingle(val);
    }

    private void setSingle(int val) throws SerialPortException {
        run(
            builder().cmdWriteRegister(0x2002+channel()-1,
                new MbData(new byte[]{0, (byte) (val==0?0:1)})
            )
        );
    }

    private void setAll(int val) throws SerialPortException {
        device().run(
            device().builder().cmdWriteRegister(0x200B,
                new MbData(new byte[]{0, (byte)val})
            )
        );
    }

    @Override
    public void set(int[] val) throws SerialPortException {
        // only if channel = 0
        assert (channel()==0);
        if (channel()==0) {
            setAll(val);
        }
    }

    private void setAll(int[] val) throws SerialPortException {
        device().run(
            device().builder().cmdWriteRegister(0x200B,
                new MbData(new byte[]{0, /*(byte)*/new ArrayToInt(val).toByte() })
            )
        );
    }
}