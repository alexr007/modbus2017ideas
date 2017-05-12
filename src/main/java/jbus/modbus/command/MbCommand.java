package jbus.modbus.command;

import jbase.hex.BytesAsHex;
import jbase.primitives.Bytes;

/**
 * Created by alexr on 20.01.2017.
 */
public class MbCommand implements Bytes {
    private final Bytes[] source;

    public MbCommand(Bytes... source) {
        this.source = source;
    }

    @Override
    public byte[] get() {
        MbMerged cmd = new MbMerged(source);
        return
            new MbMerged(
                cmd,
                new MbCRC(cmd)
            ).get();
    }

    @Override
    public String toString() {
        return new BytesAsHex(
            get()
        ).toString();
    }
}
