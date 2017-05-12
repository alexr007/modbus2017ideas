package jwad.channels;

import common.sw.common.IntToArray;
import jbus.modbus.response.*;
import jssc.SerialPortException;
import jwad.ModBusAbstractDevice;
import jwad.WadDevType;

/**
 * Created by alexr on 22.01.2017.
 */
final public class WAD_AIK_Channel implements WAD_Channel {
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
            new RqInfo(device.id(), RsParsed.cmdRead, 8)
        ).get();
        return new Values.Multiple(
            new int[]{
                (data[0] & 0xFF) << 8 | data[1] & 0xFF,
                (data[2] & 0xFF) << 8 | data[3] & 0xFF,
                (data[4] & 0xFF) << 8 | data[5] & 0xFF,
                (data[6] & 0xFF) << 8 | data[7] & 0xFF
            });
    }

    private Values getSingle() throws SerialPortException, InvalidModBusResponse {
        int[] data = new RsAnalyzed(
            device.run(device.builder.cmdReadRegister(0x100B+channel-1)),
            new RqInfo(device.id(), RsParsed.cmdRead, 2)
        ).get();
        return
            new Values.Single(
                (data[0] & 0xFF) << 8 | data[1] & 0xFF
            );
    }

    @Override
    /*
     * Младшие четыре бита регистра статуса указывают
     * на наличие связи с соответствующим каналом
     *
     * логика отличная !!! от логики DI, DI14, DIO
     * это НЕ обрыв линии
     *
     * 1 - ЕСТЬ связь с контроллером порта
     * 0 - НЕТ связи с контроллером порта (порт сгорел)
     */
    public Values fail() throws InvalidModBusResponse, SerialPortException {
        if (channel==0) {
            return
                new Values.Multiple(new IntToArray((~getFailAll())&0b1111,4).get());
        } else
            return
                new Values.Single((~getFailAll()) >> (channel-1) & 0b1);
    }

    private int getFailAll() throws SerialPortException, InvalidModBusResponse {
        RsAnalyzed analyzed = new RsAnalyzed(
            device.run(device.builder.cmdReadRegister(0x100A)),
            new RqInfo(device.id(),RsParsed.cmdRead,2)
        );
        return analyzed.get(1);
    }

    @Override
    public WadDevType type() {
        return WadDevType.AIK;
    }
}