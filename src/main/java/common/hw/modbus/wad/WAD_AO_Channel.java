package common.hw.modbus.wad;

import common.hw.modbus.InvalidModBusFunction;
import common.hw.modbus.command.MbData;
import common.hw.modbus.command.MbMerged;
import common.hw.modbus.response.*;
import common.sw.primitives.Word;
import jssc.SerialPortException;

/**
 * Created by alexr on 22.01.2017.
 */
final public class WAD_AO_Channel implements WAD_Channel {
    private final int channel;
    private final ModBusAbstractDevice device;

    public WAD_AO_Channel(int channel, ModBusAbstractDevice device) {
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
            device.run(device.builder.cmdReadRegister(0x200C,0x0004)),
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
            device.run(device.builder.cmdReadRegister(0x200C+channel-1)),
            new RqInfo(device.deviceId, RsParsed.cmdRead, 2)
        ).get();
        return
            new Values.Single(
                (data[0]<<8)+data[1]
            );
    }

    @Override
    public void set(int val) throws SerialPortException {
        assert (channel>0);
        device.run(
            device.builder.cmdWriteRegister(0x200C+channel-1,
                new MbData(new Word(val))
            )
        );
    }

    @Override
    public void set(int[] val) throws SerialPortException {
        assert (channel==0)&&(val.length==device.properties.channels());
        device.run(
            device.builder.cmdWriteRegister(0x200C+channel-1,0x0004,
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
