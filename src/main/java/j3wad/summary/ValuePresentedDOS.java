package j3wad.summary;

import j3wad.chanvalue.TypeDO;

/**
 * Created by alexr on 01.05.2017.
 */
public class ValuePresentedDOS implements ValuePresented {
    private final int ON = 1;
    private final String S_ON = "ON";
    private final String S_OFF = "OFF";
    private final int origin;

    public ValuePresentedDOS(int origin) {
        this.origin = origin;
    }

    @Override
    public String toString() {
        return (
            origin==ON ? TypeDO.ON : TypeDO.OFF
            ).toString();
    }
}
