package jtools.ranges.test;

import jbus.modbus.response.Values;
import jtools.ranges.Percentage;
import jtools.ranges.RangeValues;

/**
 * Created by alexr on 19.02.2017.
 */
public class Test_Percentage {
    public static void main6(String[] args) {
        Percentage percentage = new Percentage(new RangeValues(15000, 45000));
        System.out.println(percentage.get(25));
/*
        Values.Single single = new Values.Single(1);
        Values.Multiple multiple = new Values.Multiple(new int[]{1, 2, 3});
        System.out.println(single);
        System.out.println(multiple);
*/
    }
}
