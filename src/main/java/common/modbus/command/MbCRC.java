package common.modbus.command;

import common.primitives.Bytes;
import common.primitives.Word;

public class MbCRC implements Bytes {

    private final byte[] source;
    private final int length;
    private Word crcCached;
    private boolean ready = false;

    public MbCRC(Bytes source) {
        this(source.get(), source.get().length);
    }

    public MbCRC(byte[] source) {
        this(source, source.length);
    }

    public MbCRC(byte[] source, int length) {
        this.source = source;
        this.length = length;
    }

    // MbCRC тип byte[]
    @Override
    public byte[] get() {
        return
            new byte[] {
                (byte) crc().hi().get(),
                (byte) crc().lo().get()
            };
    }

    // MbCRC тип Word
    private Word crc() {
        if (!ready) {
            crcCached = new Word(crcCore(0xFFFF, source, length));
            ready = true;
        }
        return crcCached;
    }

    private int crcCore(int prev_crc, byte[] buf, int size)
    {
        int index = 0;
        int crc = 0xFFFF & ((prev_crc << 8) | (prev_crc >> 8));

        while ( size-- > 0 ) {
            crc ^= buf[index++] & 0xFF;

            byte bit_counter=0;
            while ( bit_counter++ < 8 ) {
                if ((crc & 0x0001) != 0) {
                    crc >>= 1;
                    crc ^= 0xA001;
                } else {
                    crc >>= 1;
                }
            }
        }

        return (0xFFFF & ((crc << 8) | (crc >> 8)));
    }

    public String toString() {
        return "crc:["+ crc()+"]";
    }

    // if crc == 0 then package is ok
    public boolean valid() {
        return
            (crc().hi().get() == 0)&&
            (crc().lo().get()==0);
    }

}