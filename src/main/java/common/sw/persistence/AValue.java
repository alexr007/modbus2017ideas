package common.sw.persistence;

/**
 * Created by alexr on 25.04.2017.
 */
public class AValue extends AbtractValue {
    final int value;

    /**
     * @param value should be 0..65535
     */
    public AValue(int value) {
        this.value = value;
    }

    public int get() {
        return this.value;
    }

    @Override
    public String toString() {
        return String.format("A:%d",this.value);
    }

    @Override
    public VT type() {
        return VT.A;
    }
}
