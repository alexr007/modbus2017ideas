package common.ranges;

import java.math.BigDecimal;

/**
 * Created by alexr on 07.02.2017.
 */
public class Percentage {
    private final RangeValues range;

    public Percentage(RangeValues range) {
        this.range = range;
    }

    public Integer get(int value) {
        return new LinearMapper(range).byPlace(BigDecimal.valueOf(value/100f));
    }
}
