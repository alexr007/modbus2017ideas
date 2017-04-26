package common.sw.common.test;

import common.sw.common.BytesAsHex;
import common.hw.modbus.command.MbData;
import common.sw.primitives.Word;

/**
 * Created by alexr on 19.02.2017.
 */
public class Test_BytesAsHex_2 {
    public static void main(String[] args) {
        MbData mbData = new MbData(new Word(65535));
        System.out.println(
            new BytesAsHex(mbData)
        );
    }
}
