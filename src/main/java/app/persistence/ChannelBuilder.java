package app.persistence;

import app.persistence.init.chan.ChanEntry;
import app.persistence.init.chan.ChannelList;
import app.persistence.init.chan.ModBusChannels;
import app.persistence.init.dev.ModBusDevices;
import constants.ChanName;
import constants.DevName;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static constants.Ch.*;
import static constants.ChanName.*;
import static constants.DevName.*;

public class ChannelBuilder {
    public static List<Pair<DevName, ChannelList>> list() {
        return
//            arrayListEcoAlliance();
            arrayListTestEnvironment();
    }

    public static Map<ChanName, Integer> mapChanCount() {
        return
            list().stream()
                .flatMap(item -> item.getValue1().list().stream())
                .collect(Collectors.toMap(
                    ChanEntry::name,
                    ChanEntry::tailSize
                ));
    }

    private static List<Pair<DevName, ChannelList>> arrayListEcoAlliance() {
        return Arrays.asList(
                // AIK1
                new Pair<>(AIK1,
                    new ChannelList(
                        new ChanEntry(S_A_CRUSH,  n1),
                        new ChanEntry(S_А_GRAN,   n2),
                        new ChanEntry(S_VOLT_IN,  n3)
                    )),
                // AIK2
                new Pair<>(AIK2,
                    new ChannelList(
                        new ChanEntry(S_TG_TEMP,  n1),
                        new ChanEntry(S_RAW_T_IN, n2),
                        new ChanEntry(S_RAW_T_OUT, n3),
                        new ChanEntry(S_TG_PRESS, n4)
                    )),
                // AO1
                new Pair<>(AO1,
                    new ChannelList(
                        new ChanEntry(M_FU,  n1),
                        new ChanEntry(M_AIR1, n2),
                        new ChanEntry(M_AIR2, n3)
                    )),
                // AO2
                new Pair<>(AO2,
                    new ChannelList(
                        new ChanEntry(M_AIR3,  n1),
                        new ChanEntry(M_DRDR, n2),
                        new ChanEntry(M_CYCL, n3),
                        new ChanEntry(M_DISPEN, n4)
                    )),
                new Pair<>(DI1,
                    new ChannelList(
                        new ChanEntry(S_CYL1_MIN, n1),
                        new ChanEntry(S_CYL1_MAX, n2),
                        new ChanEntry(S_CYL2_MIN, n3),
                        new ChanEntry(S_CYL2_MAX, n4),
                        new ChanEntry(SM_HYDRO, n5),
                        new ChanEntry(SM_RAW, n6),
                        new ChanEntry(SM_TRANS_1, n7),
                        new ChanEntry(SM_SEP, n8),
                        new ChanEntry(SM_TRANS_2, n9),
                        new ChanEntry(SM_GATE_1, n10),
                        new ChanEntry(STOP_1, n11),
                        new ChanEntry(AUTO_MAN_1, n12),
                        new ChanEntry(SM_FU_TR, n13),
                        new ChanEntry(S_FU_BK_MIN, n14),
                        new ChanEntry(S_FU_BK_MAX, n15)
                    )),
                new Pair<>(DI2,
                    new ChannelList(
                        new ChanEntry(S_CRUSH_SEC, n1),
                        new ChanEntry(SМ_FU_HAYM, n2),
                        new ChanEntry(SM_FU, n3),
                        new ChanEntry(SM_AIR1, n4),
                        new ChanEntry(SM_AIR2, n5),
                        new ChanEntry(SM_AIR3, n6),
                        new ChanEntry(SM_DRDR, n7),
                        new ChanEntry(SM_CYCL, n8),
                        new ChanEntry(SM_GATE_2, n9),
                        new ChanEntry(SM_CRUSH, n10),
                        new ChanEntry(SM_FAN, n11),
                        new ChanEntry(SM_GATE_3, n12),
                        new ChanEntry(STOP_2, n13),
                        new ChanEntry(AUTO_MAN_2, n14)
                        // 15 free
                    )),
                new Pair<>(DI3,
                    new ChannelList(
                        new ChanEntry(STOP_3, n1),
                        new ChanEntry(AUTO_MAN_3, n2),
                        new ChanEntry(S_BUNK_MIN, n3),
                        new ChanEntry(S_BUNK_MAX, n4),
                        new ChanEntry(S_GRAN_SEC, n5),
                        new ChanEntry(SM_HAYM, n6),
                        new ChanEntry(SM_DISPEN, n7),
                        new ChanEntry(SM_FORCE, n8),
                        new ChanEntry(SM_GRAN, n9),
                        new ChanEntry(SM_TRANS_3, n10),
                        new ChanEntry(SM_GATE_4, n11),
                        new ChanEntry(S_COOL_MIN, n12),
                        new ChanEntry(S_COOL_MAX, n13),
                        new ChanEntry(S_OUT_OP, n14),
                        new ChanEntry(S_OUT_CL, n15)
                    )),
                new Pair<>(DI4,
                    new ChannelList(
                        new ChanEntry(SM_EXHAU, n1),
                        new ChanEntry(SM_OUT_OP, n2),
                        new ChanEntry(SM_OUT_CL, n3),
                        new ChanEntry(SM_SIEVE, n4),
                        new ChanEntry(SM_TRANS_4, n5),
                        new ChanEntry(SM_VIBRO_FILTR, n6)
                        // 7-15 free
                    )),
                new Pair<>(DOS1,
                    new ChannelList(
                        new ChanEntry(VALVE_1, n1),
                        new ChanEntry(VALVE_2, n2),
                        new ChanEntry(VALVE_3, n3),
                        new ChanEntry(VALVE_4, n4),
                        new ChanEntry(VALVE_START, n5),
                        new ChanEntry(M_HYDRO, n6),
                        new ChanEntry(M_RAW, n7),
                        new ChanEntry(M_TRANS_1, n8)
                    )),
                new Pair<>(DOS2,
                    new ChannelList(
                        new ChanEntry(M_SEP, n1),
                        new ChanEntry(M_TRANS_2, n2),
                        new ChanEntry(M_GATE_1, n3),
                        new ChanEntry(M_FU_TR, n4),
                        new ChanEntry(M_FU_ON, n5),
                        new ChanEntry(M_AIR1_ON, n6),
                        new ChanEntry(M_AIR2_ON, n7),
                        new ChanEntry(M_AIR3_ON, n8)
                    )),
                new Pair<>(DOS3,
                    new ChannelList(
                        new ChanEntry(М_FU_HAYM, n1),
                        new ChanEntry(M_DRDR_ON, n2),
                        new ChanEntry(M_CYCL_ON, n3),
                        new ChanEntry(M_GATE_2, n4),
                        new ChanEntry(M_CRUSH, n5),
                        new ChanEntry(M_FAN, n6),
                        new ChanEntry(M_GATE_3, n7)
                        // 8 free
                    )),
                new Pair<>(DOS4,
                    new ChannelList(
                        new ChanEntry(M_HAYM, n1),
                        new ChanEntry(M_DISPEN_ON, n2),
                        new ChanEntry(M_FORCE, n3),
                        new ChanEntry(M_GRAN, n4),
                        new ChanEntry(M_TRANS_3, n5),
                        new ChanEntry(M_GATE_4, n6),
                        new ChanEntry(M_EXHAUST, n7),
                        new ChanEntry(M_OUT_OPEN, n8)
                    )),
                new Pair<>(DOS5,
                    new ChannelList(
                        new ChanEntry(M_OUT_CLOSE, n1),
                        new ChanEntry(М_SIEVE, n2),
                        new ChanEntry(M_TRANS_4, n3),
                        new ChanEntry(M_VIBRO_FILTR, n4)
                        // 5-8 free
                    ))
            );
    }

    private static List<Pair<DevName, ChannelList>> arrayListTestEnvironment() {
        return Arrays.asList(
            new Pair<>(AO1,
                new ChannelList(
                    new ChanEntry(V10_1,  n1),
                    new ChanEntry(V10_2,  n2, 3),
                    new ChanEntry(V10_3,  n3)
                )),
            new Pair<>(DI1,
                new ChannelList(
                    new ChanEntry(DC1, n1),
                    new ChanEntry(DC2, n2),
                    new ChanEntry(DC3, n3),
                    new ChanEntry(DC4, n4),
                    new ChanEntry(DC5, n5),
                    new ChanEntry(DC6, n6),
                    new ChanEntry(DC7, n7),
                    new ChanEntry(DC8, n8),
                    new ChanEntry(DC9, n9),
                    new ChanEntry(DC10, n10),
                    new ChanEntry(DC11, n11),
                    new ChanEntry(DC12, n12),
                    new ChanEntry(DC13, n13),
                    new ChanEntry(DC14, n14),
                    new ChanEntry(DC15, n15)
                )),
            new Pair<>(DOS1,
                new ChannelList(
                    new ChanEntry(R1, n1),
                    new ChanEntry(R2, n2),
                    new ChanEntry(R3, n3),
                    new ChanEntry(R4, n4),
                    new ChanEntry(R5, n5),
                    new ChanEntry(R6, n6),
                    new ChanEntry(R7, n7),
                    new ChanEntry(R8, n8)
                ))
        );
    }
}
