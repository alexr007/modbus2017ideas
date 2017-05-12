package jwad.channels;

import common.sw.common.IntToArray;
import jbus.modbus.response.*;
import jssc.SerialPortException;
import jwad.modules.WadAbstractDevice;

/**
 * Created by alexr on 22.01.2017.
 */
final public class WAD_AIK_Channel extends WadAbstractChannel implements WAD_Channel {

    /**
     * @param channel modbus channel id
     *                1..N - mean single channel
     *                0    - mean all channels (group operation).
     *                not all functions supports group operation
     * @param device  modbus real device
     */
    public WAD_AIK_Channel(int channel, WadAbstractDevice device) {
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
        int[] data = new RsAnalyzed(
            run(builder().cmdReadRegister(0x100B, 0x0004)),
            new RqInfo(device().id(), RsParsed.cmdRead, 8)
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
            run(builder().cmdReadRegister(0x100B+channel()-1)),
            new RqInfo(device().id(), RsParsed.cmdRead, 2)
        ).get();
        return
            new Values.Single(
                (data[0] & 0xFF) << 8 | data[1] & 0xFF
            );
    }

    @Override
    /**
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
        if (channel()==0) {
            return
                new Values.Multiple(new IntToArray((~getFailAll())&0b1111,4));
        } else
            return
                new Values.Single((~getFailAll()) >> (channel()-1) & 0b1);
    }

    private int getFailAll() throws SerialPortException, InvalidModBusResponse {
        RsAnalyzed analyzed = new RsAnalyzed(
            run(builder().cmdReadRegister(0x100A)),
            new RqInfo(device().id(),RsParsed.cmdRead,2)
        );
        return analyzed.get(1);
    }
}