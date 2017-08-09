package jwad.channels;

import jssc.SerialPortException;
import jbase.primitives.Word;
import jbus.modbus.command.MbData;
import jbus.modbus.command.MbMerged;
import jbus.modbus.response.*;
import jwad.modules.WadAbstractDevice;

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
    protected Values getMultiple() throws SerialPortException, InvalidModBusResponse {
        return
            new Values.Multiple(
                new WordsFrom2Bytes( device().read_(0x200C,0x0004))
            );
    }

    @Override
    protected Values getSingle() throws SerialPortException, InvalidModBusResponse {
        return
            new Values.Single(
                new WordsFrom2Bytes( device().read_(0x200C+ chanNumber()-1))
            );
    }

    @Override
    public void setUnSafe(int val) throws SerialPortException {
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
        } catch (SerialPortException e) {
            throw new IllegalArgumentException(e);
        }
    }
}