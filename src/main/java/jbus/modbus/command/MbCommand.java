package jbus.modbus.command;

import jbase.hex.HexFromBytes;
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
    public byte[] bytes() {
        MbMerged cmd = new MbMerged(source);
        return
            new MbMerged(
                cmd,
                new MbCRC(cmd)
            ).bytes();
    }

    @Override
    public String toString() {
        return new HexFromBytes(
            bytes()
        ).toString();
    }
}
