package common.sw.persistence.lifeless_ideas;

import common.sw.persistence.TypeChan;
import common.sw.persistence.TypeDO;

/**
 * Created by alexr on 25.04.2017.
 */
public class DOValue extends AbtractValue {
    private final TypeDO value;

    public DOValue(TypeDO value) {
        this.value = value;
    }

    public TypeDO get() {
        return this.value;
    }

    @Override
    public String toString() {
        return String.format("DO:%s",this.value.toString());
    }

    @Override
    public TypeChan type() {
        return TypeChan.DO;
    }
}
