package __ideas.validator;

import __ideas.ranges.RangeValues;

import java.util.ArrayList;
import java.util.Arrays;

public class ValidValues {

    private ArrayList<RangeValues> list;

    // основной конструктор - ArrayList<Range>
    public ValidValues(ArrayList<RangeValues> list) {
        this.list = list;
    }

    // дополнительный конструктор - Range[] ranges
    public ValidValues(RangeValues[] ranges)
    {
        this(
                new ArrayList<RangeValues>(
                        Arrays.asList(ranges)
                )
        );
    }

    public boolean valid(Integer value)
    {
        for (RangeValues r : list)
            if (r.contains(value)) return true;

        return false;
    }
}
