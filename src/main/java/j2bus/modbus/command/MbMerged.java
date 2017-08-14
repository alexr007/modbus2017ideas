package j2bus.modbus.command;

import j1base.hex.HexFromBytes;
import j1base.primitives.Bytes;

/**
 * Created by alexr on 18.01.2017.
 */
public class MbMerged implements Bytes {

    private final Bytes[] source;
    private final int newTotalLength;
    private boolean ready = false;
    private byte[] command;

    public MbMerged(Bytes... sources) {
        source = new Bytes[sources.length];
        int len=0;
        for (int i = 0; i < sources.length; i++) {
            source[i] = sources[i];
            len += sources[i].bytes().length;
        }
        this.newTotalLength=len;
    }

    private byte[] doBuild() {
        int index = 0;
        byte[] cmd = new byte[newTotalLength];
        for (Bytes line : source) {
            for (Byte b : line.bytes()) {
                cmd[index++] = b;
            }
        }
        return cmd;
    }

    @Override
    public byte[] bytes() {
        if (!ready) {
            command = doBuild();
            ready = true;
        }
        return command;
    }

    @Override
    public String toString() {
        return new HexFromBytes(
            bytes()
        ).toString();
    }
}
