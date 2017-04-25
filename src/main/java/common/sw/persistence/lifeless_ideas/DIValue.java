package common.sw.persistence.lifeless_ideas;

import common.sw.persistence.TypeChan;
import common.sw.persistence.TypeDI;

/**
 * Created by alexr on 25.04.2017.
 */
public class DIValue extends AbtractValue {
    private final TypeDI value;

    public DIValue(TypeDI value) {
        this.value = value;
    }

    public TypeDI get() {
        return this.value;
    }

    @Override
    public String toString() {
        return String.format("DI:%s",this.value.toString());
    }

    @Override
    public TypeChan type() {
        return TypeChan.DI;
    }
}
