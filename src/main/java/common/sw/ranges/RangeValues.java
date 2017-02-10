package common.sw.ranges;

import com.google.common.collect.Range;

/*
This is decorator class for guava.Range
 */

public class RangeValues {

    private final Range<Integer> range;

    public RangeValues(Integer min, Integer max) {
        this.range = Range.closed(min, max);
    }

    Integer upperEndpoint()
    {
        return range.upperEndpoint();
    }

    Integer lowerEndpoint()
    {
        return range.lowerEndpoint();
    }

    public boolean contains(Integer needle)
    {
        return range.contains(needle);
    }

}
