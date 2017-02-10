package common.sw.ranges;

public class RangeMapped {
    private final RangeValues src;
    private final RangeValues dst;

    public RangeMapped(RangeValues src, RangeValues dst) {
        this.src = src;
        this.dst = dst;
    }

    public RangeMapped(Integer src_low, Integer src_high, Integer dst_low, Integer dst_high) {
        this(
            new RangeValues(src_low, src_high),
            new RangeValues(dst_low, dst_high)
        );
    }

    public RangeValues src() {
        return this.src;
    }

    public RangeValues dst() {
        return this.dst;
    }

    public Integer map(Integer source)
    {
        return
            new LinearMapper(dst).byPlace(
                new LinearMapper(src).getPlace(source)
            );
    }

}
