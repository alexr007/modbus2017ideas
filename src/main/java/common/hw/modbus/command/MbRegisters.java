package common.hw.modbus.command;

import common.sw.primitives.Bytes;
import common.sw.primitives.Word;

/**
 * Created by alexr on 21.01.2017.
 *
 * Class to represent:
 * BASE address of registers and
 * COUNT of registers
 *
 */
public class MbRegisters implements Bytes {
    private final Word base;
    private final Word count;

    public MbRegisters(Word base, Word count) {
        this.base = base;
        this.count = count;
    }

    public Word base() {
        return base;
    }

    public Word count() {
        return count;
    }

    @Override
    public byte[] get() {
        return
            new MbMerged(
                base.toBytes(),
                count.toBytes()
            ).get();
    }
}
