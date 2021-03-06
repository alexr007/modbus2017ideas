package j2bus.modbus;

import j1base.primitives.Bytes;

/**
 * Created by alexr on 19.01.2017.
 *
 * functions to read/write ModBus registers
 *
 * cmdReadRegister(iface base)
 * cmdReadRegister(iface base, iface count)
 * cmdWriteRegister(iface base, Bytes content)
 * cmdWriteRegister(iface base, Bytes content)
 *
 */
public final class ModBusRequestBuilder {
    private final ModBusPacketBuilder builder;

    public ModBusRequestBuilder(int deviceId) {
        this.builder = new ModBusPacketBuilder(deviceId);
    }

    /*
     * read single register (Word)
     * at @base address
     */
    public MbRequest cmdReadRegister(int base) {
        return
            new MbRequest(
                builder.cmdReadRegisters(base, 0x0001)
            );
    }
    /*
     * read multiple registers (each Word)
     * at @base address
     * quantity @count
     */
    public MbRequest cmdReadRegister(int base, int count) {
        return
            new MbRequest(
                builder.cmdReadRegisters(base, count)
            );
    }

    /*
     * write single register (Word)
     * at @base address
     * @content - length should be 2
     */
    public MbRequest cmdWriteRegister(int base, Bytes content) {
        return
            new MbRequest(
                builder.cmdWriteRegisters(base, 0x0001, content)
            );
    }
    /*
     * write multiple registers (each Word)
     * at @base address
     * quantity @count
     * @content - length should be a multiple of 2
     */
    public MbRequest cmdWriteRegister(int base, int count, Bytes content) {
        return
            new MbRequest(
                builder.cmdWriteRegisters(base, count, content)
            );
    }

}
