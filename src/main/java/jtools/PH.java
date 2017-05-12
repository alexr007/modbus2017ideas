package jtools;

import jbase.hex.BytesAsHex;
import jbase.primitives.Bytes;
import jbus.modbus.MbRequest;
import jbus.modbus.MbResponse;

/**
 * Created by alexr on 21.01.2017.
 */
public class PH {
    public PH(String text, byte[] origin) {
        System.out.println(text+":"+new BytesAsHex(origin));
    }
    public PH(byte[] origin) {
        System.out.println(new BytesAsHex(origin));
    }
    public PH(String text, MbResponse origin) {
        System.out.println(text+":"+new BytesAsHex(origin.get()));
    }
    public PH(MbResponse origin) {
        System.out.println(new BytesAsHex(origin.get()));
    }
    public PH(String text, MbRequest origin) {
        System.out.println(text+":"+new BytesAsHex(origin.get()));
    }
    public PH(MbRequest origin) {
        System.out.println(new BytesAsHex(origin.get()));
    }
    public PH(String text, Bytes origin) {
        System.out.println(text+":"+new BytesAsHex(origin.get()));
    }
    public PH(Bytes origin) {
        System.out.println(new BytesAsHex(origin.get()));
    }
    public PH(String text, int origin) {
        System.out.println(text+":"+origin);
    }
    public PH(int origin) {
        System.out.println(origin);
    }
    public PH(String text, boolean origin) {
        System.out.println(text+":"+origin);
    }
}
