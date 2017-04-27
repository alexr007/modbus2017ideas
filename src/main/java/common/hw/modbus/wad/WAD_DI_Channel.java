package common.hw.modbus.wad;

import common.sw.common.IntToArray;
import common.hw.modbus.InvalidModBusFunction;
import common.hw.modbus.response.*;
import jssc.SerialPortException;

/**
 * Created by alexr on 22.01.2017.
 */
final public class WAD_DI_Channel implements WAD_Channel {
    private final int channel;
    private final ModBusAbstractDevice device;

    public WAD_DI_Channel(int channel, ModBusAbstractDevice device) {
        assert (channel>=0)&&(channel<=device.properties.channels());
        this.channel = channel;
        this.device = device;
    }

    @Override
    public Values get() throws InvalidModBusResponse, SerialPortException {
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
                        device.run(device.builder.cmdReadRegister(0x200D)),
                        new RqInfo(device.deviceId, RsParsed.cmdRead, 2)
                    ).get(1),8
                )
            );
    }

    private Values getSingle() throws SerialPortException, InvalidModBusResponse {
        return
            new Values.Single(
                new RsAnalyzed(
                    device.run(device.builder.cmdReadRegister(0x2004+channel-1)),
                    new RqInfo(device.deviceId, RsParsed.cmdRead, 2)
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
        if (channel==0) {
            return failMultiple();
        } else
            return failSingle();
    }

    private Values failMultiple() throws InvalidModBusResponse, SerialPortException {
        return
            new Values.Multiple(
                new IntToArray(failAll(),8)
            );
    }

    private Values failSingle() throws InvalidModBusResponse, SerialPortException {
        return
            new Values.Single(
                (failAll()>>(channel-1))&0b1
            );
    }

    private int failAll() throws SerialPortException, InvalidModBusResponse {
        return
            new RsAnalyzed(
                device.run(device.builder.cmdReadRegister(0x200E)),
                new RqInfo(device.deviceId, RsParsed.cmdRead, 2)
            ).get(1);
    }

    @Override
    public boolean opened() throws InvalidModBusResponse, SerialPortException {
        if (channel==0) {
            return (getMultiple().get()&0b11111111)==0x0;
        } else
            return getSingle().get()==0;
    }

    @Override
    public boolean closed() throws InvalidModBusResponse, SerialPortException {
        if (channel==0) {
            return (getMultiple().get()&0b11111111)==0b11111111;
        } else
            return getSingle().get()==1;
    }

    @Override
    public WADdeviceType type() {
        return WADdeviceType.DI;
    }
}
