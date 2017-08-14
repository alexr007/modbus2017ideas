package _IDEAS_.decision.test;

import _IDEAS_.decision.DecisionHS;
import java.util.Arrays;
import java.util.HashSet;
import static constants.ChanName.*;

/**
 * Created by mac on 25.07.2017.
 */
public class DecisionHSTest {
    public static void main(String[] args) {
        DecisionHS decision = new DecisionHS(
            // input sensors
            new HashSet<>(Arrays.asList(
                S_CYL1_MIN,
                S_CYL1_MAX,
                S_CYL2_MIN,
                S_CYL2_MAX,
                SM_HYDRO,
                SM_RAW,
                SM_TRANS_1,
                SM_SEP,
                SM_TRANS_2,
                SM_GATE_1,
                STOP_1,
                AUTO_MAN_1
            )),
            // output performers
            new HashSet<>(Arrays.asList(
                VALVE_1,
                VALVE_2,
                VALVE_3,
                VALVE_4,
                VALVE_START,
                M_HYDRO,
                M_RAW,
                M_TRANS_1,
                M_SEP,
                M_TRANS_2,
                M_GATE_1
            ))
        );
    }
}
