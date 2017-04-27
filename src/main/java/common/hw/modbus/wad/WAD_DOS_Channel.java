package common.hw.modbus.wad;

import common.sw.common.ArrayToInt;
import common.sw.common.IntToArray;
import common.hw.modbus.InvalidModBusFunction;
import common.hw.modbus.command.MbData;
import common.hw.modbus.response.*;
import jssc.SerialPortException;


/**
 * Created by alexr on 21.01.2017.
 */
final public class WAD_DOS_Channel implements WAD_Channel {
    private final int channel;
    private final ModBusAbstractDevice device;

    /*
     * channel selection
     * @channel: 1-8 - mean single channel
     *           0   - mean all channels;
     *
     */
    public WAD_DOS_Channel(int channel, ModBusAbstractDevice device) {
        assert (channel>=0)&&(channel<=device.properties.channels());
        this.channel = channel;
        this.device = device;
    }

    @Override
    public Values get() throws SerialPortException, InvalidModBusResponse {
        if (channel==0) {
            return getMultiple();
        } else
            return getSingle();
    }

    private Values getMultiple() throws SerialPortException, InvalidModBusResponse {
        return
            new Values.Multiple(
                new IntToArray(
                    new RsAnalyzed(
                        device.run(device.builder.cmdReadRegister(0x200B)),
                        new RqInfo(device.deviceId,RsParsed.cmdRead,2)
                    ).get(1)
                )
            );
    }

    private Values getSingle() throws SerialPortException, InvalidModBusResponse {
        return
            new Values.Single(
                new RsAnalyzed(
                    device.run(device.builder.cmdReadRegister(0x2002+channel-1)),
                    new RqInfo(device.deviceId,RsParsed.cmdRead,2)
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
        assert channel>0;
        return (get().get()==0);
    }

    @Override
    public boolean closed() throws InvalidModBusResponse, SerialPortException {
        // support only SINGLE port
        assert channel>0;
        return (get().get()==1);
    }

    @Override
    public void on() throws SerialPortException {
        if (channel==0) {
            onAll();
        } else
            onSingle();
    }

    private void onSingle() throws SerialPortException {
        device.run(
            device.builder.cmdWriteRegister(0x2002+channel-1,
                new MbData(new byte[]{0,1})
            )
        );
    }

    private void onAll() throws SerialPortException {
        setAll(0xFF);
    }

    @Override
    public void off() throws SerialPortException {
        if (channel==0) {
            offAll();
        } else
            offSingle();
    }

    private void offSingle() throws SerialPortException {
        device.run(
            device.builder.cmdWriteRegister(0x2002+channel-1,
                new MbData(new byte[]{0,0}))
        );
    }

    private void offAll() throws SerialPortException {
        setAll(0x00);
    }

    @Override
    public void set(int val) throws SerialPortException {
        if (channel==0) {
            setAll(val);
        } else
            setSingle(val);
    }

    private void setSingle(int val) throws SerialPortException {
        device.run(
            device.builder.cmdWriteRegister(0x2002+channel-1,
                new MbData(new byte[]{0, (byte) (val==0?0:1)})
            )
        );
    }

    private void setAll(int val) throws SerialPortException {
        device.run(
            device.builder.cmdWriteRegister(0x200B,
                new MbData(new byte[]{0, (byte)val})
            )
        );
    }

    @Override
    public void set(int[] val) throws SerialPortException {
        // only if channel = 0
        assert (channel==0);
        if (channel==0) {
            setAll(val);
        }
    }

    @Override
    public WADdeviceType type() {
        return WADdeviceType.DOS;
    }

    private void setAll(int[] val) throws SerialPortException {
        device.run(
            device.builder.cmdWriteRegister(0x200B,
                new MbData(new byte[]{0, /*(byte)*/new ArrayToInt(val).toByte() })
            )
        );
    }
}