package j1base.hex.test;

import j1base.hex.HexFromBytes;
import j2bus.modbus.command.MbData;
import j1base.primitives.Word;

/**
 * Created by alexr on 19.02.2017.
 */
public class Test_BytesAsHex_2 {
    public static void main(String[] args) {
        MbData mbData = new MbData(new Word(65535));
        System.out.println(
            new HexFromBytes(mbData)
        );
    }
}
