import common.sw.ranges.RandomFromRange;
import common.sw.ranges.RangeValues;
import common.sw.validator.ValidValues;
import common.sw.filters.ValueBuffer;
import common.sw.filters.ValueItem;

import java.util.*;

public class TestIdeas_OLD {

    public void test1()
    {
        RangeValues range1 = new RangeValues(1, 10);
        RangeValues range2 = new RangeValues(15, 25);

        ArrayList<RangeValues> list = new ArrayList();
        list.add(range1);
        list.add(range2);

        ValidValues vv = new ValidValues(list);
        System.out.println(vv.valid(15));
    }

    public void test2()
    {
        RangeValues range1 = new RangeValues(1, 10);
        RangeValues range2 = new RangeValues(15, 25);

        RangeValues[] r = {range1, range2};
        ValidValues vv = new ValidValues(
                new ArrayList<RangeValues>(
                        Arrays.asList(
                                r
                        )
                )
        );

        System.out.println(vv.valid(15));
    }

    public void test3()
    {
        RangeValues range1 = new RangeValues(1, 10);
        RangeValues range2 = new RangeValues(15, 25);

        RangeValues[] r = {range1, range2};
        ValidValues vv = new ValidValues(r);

        System.out.println(vv.valid(15));
    }

    public void test_buffer()
    {
        ValueBuffer buffer = new ValueBuffer(3);
        buffer.print();
        buffer.add(new ValueItem(1));
        buffer.print();


        buffer.add(new ValueItem(2));
        buffer.print();
        buffer.add(new ValueItem(3));
        buffer.print();
        buffer.add(new ValueItem(4));
        buffer.print();
        buffer.add(new ValueItem(5));
        buffer.print();
        buffer.add(new ValueItem(6));
        buffer.print();
    }

    public void test_random()
    {
        RandomFromRange rr = new RandomFromRange(new RangeValues(10, 13));
        for (int i = 0; i < 20; i++) {
            System.out.println(rr.value());
        }
    }


}
