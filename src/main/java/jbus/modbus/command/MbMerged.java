package jbus.modbus.command;

import common.sw.common.BytesAsHex;
import common.sw.primitives.Bytes;

/**
 * Created by alexr on 18.01.2017.
 */
public class MbMerged implements Bytes {

    private final Bytes[] source;
    private boolean ready = false;
    private byte[] command;
    private int newTotalLength = 0;

    public MbMerged(Bytes... sources) {
        source = new Bytes[sources.length];
        for (int i = 0; i < sources.length; i++) {
            source[i] = sources[i];
            newTotalLength += sources[i].get().length;
        }
    }

    private byte[] doBuild() {
        int index = 0;
        byte[] cmd = new byte[newTotalLength];
        for (Bytes line : source) {
            for (Byte b : line.get()) {
                cmd[index++] = b;
            }
        }
        return cmd;
    }

    @Override
    public byte[] get() {
        if (!ready) {
            command = doBuild();
            ready = true;
        }
        return command;
    }

    @Override
    public String toString() {
        return new BytesAsHex(
            get()
        ).toString();
    }
}
