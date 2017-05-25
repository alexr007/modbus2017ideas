package jwad.channels;

import jbase.arr.ArrayFromInt;
import jbus.modbus.InvalidModBusFunction;
import jbus.modbus.response.*;
import jssc.SerialPortException;
import jwad.modules.WadAbstractDevice;

/**
 * Created by alexr on 22.01.2017.
 */
final public class WAD_DI_Channel extends WadAbstractChannel implements WAD_Channel {
    /**
     * @param channel modbus channel id
     *                1..N - mean single channel
     *                0    - mean all channels (group operation).
     *                not all functions supports group operation
     * @param device  modbus real device
     */
    public WAD_DI_Channel(int channel, WadAbstractDevice device) {
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
        return
            new Values.Multiple(
                new ArrayFromInt(
                    new RsAnalyzed(
                        run(builder().cmdReadRegister(0x200D)),
                        new RqInfo(device().id(), RsParsed.cmdRead, 2)
                    ).get(1),8
                )
            );
    }

    private Values getSingle() throws SerialPortException, InvalidModBusResponse {
        return
            new Values.Single(
                new RsAnalyzed(
                    run(builder().cmdReadRegister(0x2004+channel()-1)),
                    new RqInfo(device().id(), RsParsed.cmdRead, 2)
                ).get(1)
            );
    }

    @Override
    /*
     * 200E
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
                new ArrayFromInt(failAll(),8)
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
            new RsAnalyzed(
                run(builder().cmdReadRegister(0x200E)),
                new RqInfo(device().id(), RsParsed.cmdRead, 2)
            ).get(1);
    }

    @Override
    public boolean opened() throws InvalidModBusResponse, SerialPortException {
        if (channel()==0) {
            return (getMultiple().get()&0b11111111)==0x0;
        } else
            return getSingle().get()==0;
    }

    @Override
    public boolean closed() throws InvalidModBusResponse, SerialPortException {
        if (channel()==0) {
            return (getMultiple().get()&0b11111111)==0b11111111;
        } else
            return getSingle().get()==1;
    }
}