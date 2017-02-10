package common.sw.ranges;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LinearMapper {

    private final RangeValues range;

    public LinearMapper(RangeValues range) {
        this.range = range;
    }

    public BigDecimal getPlace(int srcVal)
    {
        return BigDecimal.valueOf(
                (double)(srcVal-range.lowerEndpoint())/
                (range.upperEndpoint()-range.lowerEndpoint())
        );
    }

    public Integer byPlace(BigDecimal place)
    {
        return BigDecimal.
                valueOf(range.lowerEndpoint())
                .add(
                    BigDecimal.valueOf(range.upperEndpoint() - range.lowerEndpoint())
                            .multiply(place)
                )
                .setScale(0, RoundingMode.HALF_UP)
                .intValue();
    }

}
