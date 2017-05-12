package app.decision.test;

import jbus.modbus.response.Values;
import app.decision.Decision;
import app.decision.ValuesMap;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by alexr on 24.04.2017.
 */
public class DecisionTest {
    public void test() {
        Decision decision = new Decision(
            // source list (for information only)
            Arrays.asList("S1", "S2", "S3"),
            // destination list (for information only)
            Arrays.asList("P1", "P2", "P3", "P4")
        );


        ValuesMap output = decision.make(
            new ValuesMap(
                new HashMap<CharSequence, Values>(){{
                    put("S1",new Values.Single(11));
                    put("S2",new Values.Single(21));
                    put("S3",new Values.Single(21));
                }}
            )
        );

        System.out.println(
            String.format("output is: %s", output.entrySet())
        );
    }
}
