package common.hw.modbus.wad;

/**
 * Created by alexr on 01.05.2017.
 */
public class ValuePresentedAO {
    private final int origin;
    private final int I_NOT_INSTALLED = 0xFFFF;
    private final String S_NOT_INSTALLED = "NOT INSTALLED";

    public ValuePresentedAO(int origin) {
        this.origin = origin;
    }

    @Override
    public String toString() {
        return origin == I_NOT_INSTALLED ?
            S_NOT_INSTALLED :
            Integer.toString(origin);
    }
}
