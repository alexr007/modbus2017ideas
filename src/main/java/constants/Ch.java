package constants;

/**
 * Created by alexr on 17.02.2017.
 *
 * Channels on Device (1-15)
 */
public class Ch {
    public static final int n0 = 0; // mean all channels
    public static final int n1 = 1;
    public static final int n2 = 2;
    public static final int n3 = 3;
    public static final int n4 = 4;
    public static final int n5 = 5;
    public static final int n6 = 6;
    public static final int n7 = 7;
    public static final int n8 = 8;
    public static final int n9 = 9;
    public static final int n10 = 10;
    public static final int n11 = 11;
    public static final int n12 = 12;
    public static final int n13 = 13;
    public static final int n14 = 14;
    public static final int n15 = 15;

/*
    // AI ports 0x21
    public static final String S_A_CRUSH = "S_A_CRUSH";
    public static final String S_А_GRAN = "S_А_GRAN";
    public static final String S_VOLT_IN = "S_VOLT_IN";
    // AI ports 0x22
    public static final String S_TG_TEMP = "S_TG_TEMP";
    public static final String S_RAW_T_IN = "S_RAW_T_ IN";
    public static final String S_RAW_T_OUT = "S_RAW_T_ OUT";
    public static final String S_TG_PRESS = "S_TG_PRESS";

    // AO ports 0x41
    public static final String M_FU = "M_FU";
    public static final String M_AIR1 = "M_AIR1";
    public static final String M_AIR2 = "M_AIR2";
    // AO ports 0x42
    public static final String M_AIR3 = "M_AIR3";
    public static final String M_DRDR = "M_DRDR";
    public static final String M_CYCL = "M_CYCL";
    public static final String M_DISPEN = "M_DISPEN";

    // DI ports 0x11
    public static final String S_CYL1_MIN = "S_CYL1_MIN";
    public static final String S_CYL1_MAX = "S_CYL1_MAX";
    public static final String S_CYL2_MIN = "S_CYL2_MIN";
    public static final String S_CYL2_MAX = "S_CYL2_MAX";
    public static final String SM_HYDRO = "SM_HYDRO";
    public static final String SM_RAW = "SM_RAW";
    public static final String SM_TRANS_1 = "SM_TRANS_1";
    public static final String SM_SEP = "SM_SEP";
    public static final String SM_TRANS_2 = "SM_TRANS_2";
    public static final String SM_GATE_1 = "SM_GATE_1";
    public static final String STOP_1 = "STOP_1";
    public static final String AUTO_MAN_1 = "AUTO_MAN_1";
    public static final String SM_FU_TR = "SM_FU_TR";
    public static final String S_FU_BK_MIN = "S_FU_BK_MIN";
    public static final String S_FU_BK_MAX = "S_FU_BK_MAX";
    // DI ports 0x12
    public static final String S_CRUSH_SEC = "S_CRUSH_SEC";
    public static final String SМ_FU_HAYM = "SМ_FU_HAYM";
    public static final String SM_FU = "SM_FU";
    public static final String SM_AIR1 = "SM_AIR1";
    public static final String SM_AIR2 = "SM_AIR2";
    public static final String SM_AIR3 = "SM_AIR3";
    public static final String SM_DRDR = "SM_DRDR";
    public static final String SM_CYCL = "SM_CYCL";
    public static final String SM_GATE_2 = "SM_GATE_2";
    public static final String SM_CRUSH = "SM_CRUSH";
    public static final String SM_FAN = "SM_FAN";
    public static final String SM_GATE_3 = "SM_GATE_3";
    public static final String STOP_2 = "STOP_2";
    public static final String AUTO_MAN_2 = "AUTO_MAN_2";
    public static final String x12p15 = "free 1";
    // DI ports 0x13
    public static final String STOP_3 = "STOP_3";
    public static final String AUTO_MAN_3 = "AUTO_MAN_3";
    public static final String S_BUNK_MIN = "S_BUNK_MIN";
    public static final String S_BUNK_MAX = "S_BUNK_MAX";
    public static final String S_GRAN_SEC = "S_GRAN_SEC";
    public static final String SM_HAYM = "SM_HAYM";
    public static final String SM_DISPEN = "SM_DISPEN";
    public static final String SM_FORCE = "SM_FORCE";
    public static final String SM_GRAN = "SM_GRAN";
    public static final String SM_TRANS_3 = "SM_TRANS_3";
    public static final String SM_GATE_4 = "SM_GATE_4";
    public static final String S_COOL_MIN = "S_COOL_MIN";
    public static final String S_COOL_MAX = "S_COOL_MAX";
    public static final String S_OUT_OP = "S_OUT_OP";
    public static final String S_OUT_CL = "S_OUT_CL";
    // DI ports 0x14
    public static final String SM_EXHAU = "SM_EXHAU";
    public static final String SM_OUT_OP = "SM_OUT_OP";
    public static final String SM_OUT_CL = "SM_OUT_CL";
    public static final String SM_SIEVE = "SM_SIEVE";
    public static final String SM_TRANS_4 = "SM_TRANS_4";
    public static final String SM_VIBRO_FILTR = "SM_VIBRO_FILTR";
    public static final String x14p07 = "free 2";
    public static final String x14p08 = "free 3";
    public static final String x14p09 = "free 4";
    public static final String x14p10 = "free 5";
    public static final String x14p11 = "free 6";
    public static final String x14p12 = "free 7";
    public static final String x14p13 = "free 8";
    public static final String x14p14 = "free 9";
    public static final String x14p15 = "free 10";

    // DOS ports 0x31
    public static final String VALVE_1 = "VALVE_1";
    public static final String VALVE_2 = "VALVE_2";
    public static final String VALVE_3 = "VALVE_3";
    public static final String VALVE_4 = "VALVE_4";
    public static final String VALVE_START = "VALVE_START";
    public static final String M_HYDRO = "M_HYDRO";
    public static final String M_RAW = "M_RAW";
    public static final String M_TRANS_1 = "M_TRANS_1";
    // DOS ports 0x32
    public static final String M_SEP = "M_SEP";
    public static final String M_TRANS_2 = "M_TRANS_2";
    public static final String M_GATE_1 = "M_GATE_1";
    public static final String M_FU_TR = "M_FU_TR";
    public static final String M_FU_ON = "M_FU_ON";
    public static final String M_AIR1_ON = "M_AIR1_ON";
    public static final String M_AIR2_ON = "M_AIR2_ON";
    public static final String M_AIR3_ON = "M_AIR3_ON";
    // DOS ports 0x33
    public static final String М_FU_HAYM = "М_FU_HAYM";
    public static final String M_DRDR_ON = "M_DRDR_ON";
    public static final String M_CYCL_ON = "M_CYCL_ON";
    public static final String M_GATE_2 = "M_GATE_2";
    public static final String M_CRUSH = "M_CRUSH";
    public static final String M_FAN = "M_FAN";
    public static final String M_GATE_3 = "M_GATE_3";
    public static final String x33p8 = "not used 1";
    // DOS ports 0x34
    public static final String M_HAYM = "M_HAYM";
    public static final String M_DISPEN_ON = "M_DISPEN_ON";
    public static final String M_FORCE = "M_FORCE";
    public static final String M_GRAN = "M_GRAN";
    public static final String M_TRANS_3 = "M_TRANS_3";
    public static final String M_GATE_4 = "M_GATE_4";
    public static final String M_EXHAUST = "M_EXHAUST";
    public static final String M_OUT_OPEN = "M_OUT_OPEN";
    // DOS ports 0x35
    public static final String M_OUT_CLOSE = "M_OUT_CLOSE";
    public static final String М_SIEVE = "М_SIEVE";
    public static final String M_TRANS_4 = "M_TRANS_4";
    public static final String M_VIBRO_FILTR = "M_VIBRO_FILTR";
    public static final String x35p5 = "not used 2";
    public static final String x35p6 = "not used 3";
    public static final String x35p7 = "not used 4";
    public static final String x35p8 = "not used 5";
*/
}
