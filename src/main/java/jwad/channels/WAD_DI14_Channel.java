package jwad.channels;

import jbase.arr.ArrayFromInt;
import jbus.modbus.InvalidModBusFunction;
import jbus.modbus.response.*;
import jssc.SerialPortException;
import jwad.modules.WadAbstractDevice;

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
    public Values get() throws InvalidModBusResponse, SerialPortException {
        if (channel()==0) {
            return getMultiple();
        } else
            return getSingle();
    }

    private Values getMultiple() throws SerialPortException, InvalidModBusResponse {
        int[] data = new RsAnalyzed(
                run(builder().cmdReadRegister(0x1FFE)),
                new RqInfo(device().id(), RsParsed.cmdRead, 2)
        ).get();
        return
            new Values.Multiple(
                new ArrayFromInt(
                    (data[0] & 0xFF) << 8 | data[1] & 0xFF,
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
    /*
     * 1FFF
     * Состояние «Обрыв лини» по всем каналам
     * 1 - обрыв
     * 0 - все ОК
     *
     */
    public Values fail() throws InvalidModBusFunction, InvalidModBusResponse, SerialPortException {
        if (channel()==0) {
            return failMultiple();
        } else
            return failSingle();
    }

    private Values failMultiple() throws InvalidModBusResponse, SerialPortException {
        return
            new Values.Multiple(
                new ArrayFromInt(failAll(),15)
            );
    }

    private Values failSingle() throws InvalidModBusResponse, SerialPortException {
        return
            new Values.Single(
                (failAll()>>(channel()-1))&0b1
            );
    }

    private int failAll() throws SerialPortException, InvalidModBusResponse {
        int[] data = new RsAnalyzed(
            run(builder().cmdReadRegister(0x1FFF)),
            new RqInfo(device().id(), RsParsed.cmdRead, 2)
        ).get();
        return
            (data[0] & 0xFF) << 8 | data[1] & 0xFF;
/*
        return
            new RsAnalyzed(
                device.run(device.builder().cmdReadRegister(0x1FFF)),
                new RqInfo(device.deviceId, RsParsed.cmdRead, 2)
            ).get(1);
*/
    }

    @Override
    public boolean opened() throws InvalidModBusFunction, InvalidModBusResponse, SerialPortException {
        if (channel()==0) {
            return (getMultiple().get()&0b0111111111111111)==0x0;
        } else
            return getSingle().get()==0;
    }

    @Override
    public boolean closed() throws InvalidModBusFunction, InvalidModBusResponse, SerialPortException {
        if (channel()==0) {
            return (getMultiple().get()&0b0111111111111111)==0b0111111111111111;
        } else
            return getSingle().get()==1;
    }
}