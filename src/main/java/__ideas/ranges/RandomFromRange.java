package __ideas.ranges;

import java.math.BigDecimal;

public class RandomFromRange {

    private final RangeValues range;

    public RandomFromRange(RangeValues range) {
        this.range = range;
    }

    public Integer value()
    {
        return new LinearMapper(range).byPlace(BigDecimal.valueOf(Math.random()));
    }

}
