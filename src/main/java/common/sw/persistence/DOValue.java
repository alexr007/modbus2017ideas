package common.sw.persistence;

/**
 * Created by alexr on 25.04.2017.
 */
public class DOValue extends AbtractValue {
    private final DOType value;

    public DOValue(DOType value) {
        this.value = value;
    }

    public DOType get() {
        return this.value;
    }

    @Override
    public String toString() {
        return String.format("DO:%s",this.value.toString());
    }

    @Override
    public VT type() {
        return VT.DO;
    }
}
