package jwad.channels;

import jbase.arr.ArrayFromIntBits;
import jbus.modbus.response.*;
import jssc.SerialPortException;
import jwad.modules.WadAbstractDevice;

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
        } catch (SerialPortException e) {
            throw new IllegalArgumentException(e);
        } catch (InvalidModBusResponse e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Values getMultiple() throws SerialPortException, InvalidModBusResponse {
        return
            new Values.Multiple(
                new WordsFromBytes(
                    new RsAnalyzed(
                        run(builder().cmdReadRegister(0x100B, 0x0004)),
                        new RqInfo(device().id(), RsParsed.cmdRead, 8)
                    )
                )
            );
    }

    private Values getSingle() throws SerialPortException, InvalidModBusResponse {
        return
            new Values.Single(
                new WordsFromBytes(new RsAnalyzed(
                    run(builder().cmdReadRegister(0x100B+ chanNumber()-1)),
                    new RqInfo(device().id(), RsParsed.cmdRead, 2)
                ))
            );
    }

    /**
     * logic inverted
     * value 1 mean ERROR
     * value 0 mean OK
     */
    @Override
    public Values failsRaw() throws InvalidModBusResponse, SerialPortException {
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
    private int getFailAll() throws SerialPortException, InvalidModBusResponse {
        return
            new RsAnalyzed(
                run(builder().cmdReadRegister(0x100A)),
                new RqInfo(device().id(),RsParsed.cmdRead,2)
            ).get(1);
    }
}