package common.sw.persistence;

/**
 * Created by alexr on 25.04.2017.
 */
public class DIValue extends AbtractValue {
    private final DIType value;

    public DIValue(DIType value) {
        this.value = value;
    }

    public DIType get() {
        return this.value;
    }

    @Override
    public String toString() {
        return String.format("DI:%s",this.value.toString());
    }

    @Override
    public VT type() {
        return VT.DI;
    }
}
