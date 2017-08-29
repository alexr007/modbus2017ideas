package j3wad.channels;

import jssc.SerialPortException;
import j1base.primitives.Word;
import j2bus.modbus.command.MbData;
import j2bus.modbus.command.MbMerged;
import j2bus.modbus.response.*;
import j3wad.modules.WadAbstractDevice;

import java.io.IOException;

/**
 * Created by alexr on 22.01.2017.
 */
final public class WAD_AO_Channel extends WAD_AO_ implements WAD_Channel {
    /**
     * @param channel modbus channel id
     *                1..N - mean single channel
     *                0    - mean all channelMap (group operation).
     *                not all functions supports group operation
     * @param device  modbus real device
     */
    public WAD_AO_Channel(int channel, WadAbstractDevice device) {
        super(channel, device);
    }

    @Override
    protected Values getMultiple() {
        return
            new Values.Multiple(
                new WordsFrom2Bytes( device().read_(0x200C,0x0004))
            );
    }

    @Override
    protected Values getSingle() {
        return
            new Values.Single(
                new WordsFrom2Bytes( device().read_(0x200C+ chanNumber()-1))
            );
    }

    @Override
    public void setUnSafe(int val) throws IOException {
        assert (chanNumber()>0);
        device().write_(0x200C+ chanNumber()-1, new MbData(new Word(val)));
    }

    @Override
    public void set(int[] val)  {
        checkForGroupWrite(val.length);
        try {
            device().write_(0x200C+ chanNumber()-1,0x0004,
                new MbMerged(
                    new Word(val[0]).toBytes(),
                    new Word(val[1]).toBytes(),
                    new Word(val[2]).toBytes(),
                    new Word(val[3]).toBytes()
                ));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}