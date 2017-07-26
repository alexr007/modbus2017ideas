package app.decision.test;

import constants.ChanName;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by mac on 25.07.2017.
 */
public class SortedSetTest {
    public static void test1() {
        EnumSet<ChanName> stop1 = EnumSet.of(ChanName.STOP_1, ChanName.STOP_2);
        final EnumSet set = EnumSet.allOf(ChanName.class);

        System.out.println(set);

        SortedSet<Integer> ints = new TreeSet<>(Arrays.asList(
            11, 2, 7, 4, 5, 1
        ));

        ints.forEach((item)->{
            System.out.println(String.format("Item:%s",item.toString()));
        });
    }
}
