package j1base.hex.test;

import j1base.hex.HexFromBytes;

/**
 * Created by alexr on 19.02.2017.
 */
public class Test_BytesAsHex_1 {
    public static void main8(String[] args) {
        System.out.println(
            new HexFromBytes(new byte[] {1,1,2,2,3,3})
        );
    }
}
