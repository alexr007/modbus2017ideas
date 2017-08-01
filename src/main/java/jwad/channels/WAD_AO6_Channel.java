package jwad.channels;

import jbus.modbus.command.MbData;
import jbus.modbus.command.MbMerged;
import jbus.modbus.response.*;
import jbase.primitives.Word;
import jssc.SerialPortException;
import jwad.modules.WadAbstractDevice;

/**
 * Created by alexr on 22.01.2017.
 */
final public class WAD_AO6_Channel extends WadAbstractChannel implements WAD_Channel {
    /**
     * @param channel modbus channel id
     *                1..N - mean single channel
     *                0    - mean all channelMap (group operation).
     *                not all functions supports group operation
     * @param device  modbus real device
     */
    public WAD_AO6_Channel(int channel, WadAbstractDevice device) {
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
            run(builder().cmdReadRegister(0x2010,0x0006)),
            new RqInfo(device().id(), RsParsed.cmdRead, 12)
        ).get();
        return
            new Values.Multiple(
                new int[] {
                    (data[0] & 0xFF) << 8 | data[1] & 0xFF,
                    (data[2] & 0xFF) << 8 | data[3] & 0xFF,
                    (data[4] & 0xFF) << 8 | data[5] & 0xFF,
                    (data[6] & 0xFF) << 8 | data[7] & 0xFF,
                    (data[8] & 0xFF) << 8 | data[9] & 0xFF,
                    (data[10] & 0xFF) << 8 | data[11] & 0xFF,
                }
            );
    }

    private Values getSingle() throws SerialPortException, InvalidModBusResponse {
        int[] data = new RsAnalyzed(
            run(builder().cmdReadRegister(0x2010+channel()-1)),
            new RqInfo(device().id(), RsParsed.cmdRead, 2)
        ).get();
        return
            new Values.Single(
                (data[0] & 0xFF) << 8 | data[1] & 0xFF
            );
    }

    @Override
    public void set(int val) throws SerialPortException {
        assert (channel()>0);
        run(
            builder().cmdWriteRegister(0x2010+channel()-1,
                new MbData(new Word(val))
            )
        );
    }

    @Override
    public void set(int[] val) throws SerialPortException {
        assert (channel()==0)&&(val.length==device().properties().chanCount());
        run(
            builder().cmdWriteRegister(0x2010+channel()-1,0x0006,
                new MbMerged(
                    new Word(val[0]).toBytes(),
                    new Word(val[1]).toBytes(),
                    new Word(val[2]).toBytes(),
                    new Word(val[3]).toBytes(),
                    new Word(val[4]).toBytes(),
                    new Word(val[5]).toBytes()
                )
            )
        );
    }
}