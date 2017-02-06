package common.modbus.wad;

import common.IntToArray;
import common.modbus.response.*;
import jssc.SerialPortException;
import common.modbus.MbResponse;

/**
 * Created by alexr on 22.01.2017.
 */
final public class WAD_AIK_Channel implements Channel {
    private final int channel;
    private final ModBusAbstractDevice device;

    /*
     * channel selection
     * @channel: 1-4 - mean single channel
     *           0   - mean all channels;
     *
     */
    public WAD_AIK_Channel(int channel, ModBusAbstractDevice device) {
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
        int[] data = new RsAnalyzed(
            device.run(device.builder.cmdReadRegister(0x100B, 0x0004)),
            new RqInfo(device.deviceId, RsParsed.cmdRead, 8)
        ).get();
        return
            new Values.Multiple(
                new int[] {
                    (data[0]<<8)+data[1],
                    (data[2]<<8)+data[3],
                    (data[4]<<8)+data[5],
                    (data[6]<<8)+data[7]
                }
            );
    }

    private Values getSingle() throws SerialPortException, InvalidModBusResponse {
        int[] data = new RsAnalyzed(
            device.run(device.builder.cmdReadRegister(0x100B+channel-1)),
            new RqInfo(device.deviceId, RsParsed.cmdRead, 2)
        ).get();
        return
            new Values.Single(
                (data[0]<<8)+data[1]
            );
    }

    @Override
    public Values fail() throws ModBusInvalidFunction, InvalidModBusResponse, SerialPortException {
        if (channel==0) {
            return
                new Values.Multiple(new IntToArray(getFailAll(),4).get());
        } else
            return
                new Values.Single(getFailAll() >> (channel-1) & 0b1);
    }

    private int getFailAll() throws SerialPortException, InvalidModBusResponse {
        RsAnalyzed analyzed = new RsAnalyzed(
            device.run(device.builder.cmdReadRegister(0x100A)),
            new RqInfo(device.deviceId,RsParsed.cmdRead,2)
        );
        return analyzed.get(1);
    }

    @Override
    public boolean opened() throws ModBusInvalidFunction {
        throw new ModBusInvalidFunction();
    }

    @Override
    public boolean closed() throws ModBusInvalidFunction {
        throw new ModBusInvalidFunction();
    }

    @Override
    public void on() throws ModBusInvalidFunction {
        throw new ModBusInvalidFunction();
    }

    @Override
    public void off() throws ModBusInvalidFunction {
        throw new ModBusInvalidFunction();
    }

    @Override
    public void set(int val) throws ModBusInvalidFunction {
        throw new ModBusInvalidFunction();
    }

    @Override
    public void set(int[] val) throws ModBusInvalidFunction {
        throw new ModBusInvalidFunction();
    }
}