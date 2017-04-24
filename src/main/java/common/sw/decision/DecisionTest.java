package common.sw.decision;

import common.hw.modbus.response.Values;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by alexr on 24.04.2017.
 */
public class DecisionTest {
    public void test() {
        HashMap<CharSequence, Values> src = new HashMap<>();
        src.put("S1",new Values.Single(11));
        src.put("S2",new Values.Single(21));
        src.put("S3",new Values.Single(21));
        ValuesMap output = new Decision(
            Arrays.asList("S1", "S2", "S3"),
            Arrays.asList("P1", "P2", "P3", "P4")
        ).make(new ValuesMap(src));
        System.out.println(
            String.format("output is: %s", output.entrySet())
        );
    }
}
