package jwad.summary;

import common.sw.persistence.TypeDO;

/**
 * Created by alexr on 01.05.2017.
 */
public class ValuePresentedDOS {
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
