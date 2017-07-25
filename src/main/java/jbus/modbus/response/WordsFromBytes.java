package jbus.modbus.response;

import jbase.primitives.Word;

/**
 * Created by mac on 18.05.2017.
 *
 */
public class WordsFromBytes {
    private final int[] origin;

    public WordsFromBytes(RsAnalyzed analyzed) throws InvalidModBusResponse {
        this(analyzed.get());
    }

    public WordsFromBytes(int[] origin) {
        this.origin = origin;
    }

    /**
     * @return words FFFF, ... from sequential bytes (origin) FF,FF, ...
     */
    public int[] get() {
        int newLength = origin.length / 2;
        int[] res = new int[newLength];
        for (int i = 0 ; i < newLength ; i++) {
            res[i]=(origin[i*2] & 0xFF) << 8 | origin[i*2+1] & 0xFF;
        }
        return res;
    }

    public int get0() {
        return get()[0];
    }
}
