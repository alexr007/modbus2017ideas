package _IDEAS_.jtools.ranges.test;

import _IDEAS_.jtools.ranges.Percentage;
import _IDEAS_.jtools.ranges.RangeValues;

/**
 * Created by alexr on 19.02.2017.
 */
public class Test_Percentage {
    public static void main6(String[] args) {
        Percentage percentage = new Percentage(new RangeValues(15000, 45000));
        System.out.println(percentage.get(25));
    }
}
