package _IDEAS_.jtools.ranges.test;

import _IDEAS_.jtools.ranges.RandomFromRange;
import _IDEAS_.jtools.ranges.RangeValues;
import _IDEAS_.jtools.validator.ValidValues;

import java.util.*;

public class Test_Ranges {

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


    public void test_random()
    {
        RandomFromRange rr = new RandomFromRange(new RangeValues(10, 13));
        for (int i = 0; i < 20; i++) {
            System.out.println(rr.value());
        }
    }


}
