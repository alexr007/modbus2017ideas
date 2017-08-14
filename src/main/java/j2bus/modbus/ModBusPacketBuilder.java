package j2bus.modbus;

import j2bus.modbus.command.*;
import j1base.primitives.Bytes;
import j1base.primitives.Word;

/**
 * Created by alexr on 22.01.2017.
 */
public final class ModBusPacketBuilder {
    private final int deviceId;

    public ModBusPacketBuilder(int deviceId) {
        this.deviceId = deviceId;
    }

    public Bytes cmdReadRegisters(int base, int count) {
        return
            new MbCommand(
                new MbDeviceId(this.deviceId),
                new MbRead(),
                new MbRegisters(new Word(base), new Word(count))
            );
    }

    public Bytes cmdWriteRegisters(int base, int count, Bytes content) {
        return
            new MbCommand(
                new MbDeviceId(this.deviceId),
                new MbWrite(),
                new MbRegisters(new Word(base), new Word(count)),
                new MbLength(content.bytes()),
                new MbData(content.bytes())
            );
    }
}
