package j3wad.channels;

import j1base.arr.ArrayFromIntBits;
import j2bus.modbus.response.*;
import jssc.SerialPortException;
import j3wad.modules.WadAbstractDevice;

import java.io.IOException;

/**
 * Created by alexr on 22.01.2017.
 */
final public class WAD_AIK_Channel extends WadAbstractChannel implements WAD_Channel {

    public WAD_AIK_Channel(int channel, WadAbstractDevice device) {
        super(channel, device);
    }

    @Override
    public Values getWoFailRaw() {
        try {
            return chanNumber()==0
                ? getMultiple()
                : getSingle();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Values getMultiple() {
        return
            new Values.Multiple(
                new WordsFrom2Bytes( device().read_(0x100B, 0x0004))
            );
    }

    private Values getSingle() {
        return
            new Values.Single(
                new WordsFrom2Bytes( device().read_(0x100B+ chanNumber()-1))
            );
    }

    /**
     * logic inverted
     * value 1 mean ERROR
     * value 0 mean OK
     */
    @Override
    public Values failsRaw() {
        return chanNumber()==0
            ? new Values.Multiple(
                new ArrayFromIntBits((~getFailAll())&0b1111,4)
        )
            : new Values.Single(
                (~getFailAll()) >> (chanNumber()-1) & 0b1
        );
    }

    /**
     * @return bit mask for channel status
     * four lower bits of register
     * mean link status for corresponding channel
     * bit = 1 - link OK
     * bit = 0 - no link with channel CPU
     */
    private int getFailAll() {
        return device().read_(0x100A).get(1);
    }
}