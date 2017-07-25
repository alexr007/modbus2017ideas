package app.decision;

import constants.ChanName;
import jbus.modbus.response.Values;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by alexr on 24.04.2017.
 */
public class Decision {
    private final Iterable<CharSequence> inputSet;
    private final Iterable<CharSequence> outputSet;

    public Decision(Iterable<CharSequence> inputSet, Iterable<CharSequence> outputSet) {
        this.inputSet = inputSet;
        this.outputSet = outputSet;
    }

    public ValuesMap make(ValuesMap input) {
        System.out.println(
            String.format("inputSet: %s", inputSet)
        );
        System.out.println(
            String.format("outputSet: %s", outputSet)
        );
        System.out.println(
            String.format("input parameters: %s", input.entrySet())
        );
        HashMap<CharSequence, Values> output = new HashMap<>();

        output.put("M_1", new Values.Single(31000) );

        return new ValuesMap(output);
    }

}
