package common.hw.modbus.wad;

import common.sw.common.IntToArray;
import common.hw.modbus.InvalidModBusFunction;
import common.hw.modbus.response.*;
import jssc.SerialPortException;

/**
 * Created by alexr on 06.02.2017.
 */
final public class WAD_DI14_Channel implements WAD_Channel {
    private final int channel;
    private final ModBusAbstractDevice device;

    public WAD_DI14_Channel(int channel, ModBusAbstractDevice device) {
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
                        device.run(device.builder.cmdReadRegister(0x1FFE)),
                        new RqInfo(device.deviceId, RsParsed.cmdRead, 2)
                    ).get(1),15
                )
            );
    }

    private Values getSingle() throws SerialPortException, InvalidModBusResponse {
        return
            new Values.Single(
                new RsAnalyzed(
                    device.run(device.builder.cmdReadRegister(0x2001+channel-1)),
                    new RqInfo(device.deviceId, RsParsed.cmdRead, 2)
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
        if (channel==0) {
            return failMultiple();
        } else
            return failSingle();
    }

    private Values failMultiple() throws InvalidModBusResponse, SerialPortException {
        return
            new Values.Multiple(
                new IntToArray(failAll(),15)
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
                device.run(device.builder.cmdReadRegister(0x1FFF)),
                new RqInfo(device.deviceId, RsParsed.cmdRead, 2)
            ).get(1);
    }

    @Override
    public boolean opened() throws InvalidModBusFunction, InvalidModBusResponse, SerialPortException {
        if (channel==0) {
            return (getMultiple().get()&0b0111111111111111)==0x0;
        } else
            return getSingle().get()==0;
    }

    @Override
    public boolean closed() throws InvalidModBusFunction, InvalidModBusResponse, SerialPortException {
        if (channel==0) {
            return (getMultiple().get()&0b0111111111111111)==0b0111111111111111;
        } else
            return getSingle().get()==1;
    }

    @Override
    public WadDevType type() {
        return WadDevType.DI14;
    }
}
