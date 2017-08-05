package constants;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

/**
 * Created by mac on 22.07.2017.
 */
public enum ChanName {
    // AI ports 0x21
    S_A_CRUSH,
    S_А_GRAN,
    S_VOLT_IN,

    // AI ports 0x22
    S_TG_TEMP,
    S_RAW_T_IN,
    S_RAW_T_OUT,
    S_TG_PRESS,

    // AO ports 0x41
    M_FU,
    M_AIR1,
    M_AIR2,

    // AO ports 0x42
    M_AIR3,
    M_DRDR,
    M_CYCL,
    M_DISPEN,

    // DI ports 0x11
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
    AUTO_MAN_1,
    SM_FU_TR,
    S_FU_BK_MIN,
    S_FU_BK_MAX,

    // DI ports 0x12
    S_CRUSH_SEC,
    SМ_FU_HAYM,
    SM_FU,
    SM_AIR1,
    SM_AIR2,
    SM_AIR3,
    SM_DRDR,
    SM_CYCL,
    SM_GATE_2,
    SM_CRUSH,
    SM_FAN,
    SM_GATE_3,
    STOP_2,
    AUTO_MAN_2,
    //x12p15

    // DI ports 0x13
    STOP_3,
    AUTO_MAN_3,
    S_BUNK_MIN,
    S_BUNK_MAX,
    S_GRAN_SEC,
    SM_HAYM,
    SM_DISPEN,
    SM_FORCE,
    SM_GRAN,
    SM_TRANS_3,
    SM_GATE_4,
    S_COOL_MIN,
    S_COOL_MAX,
    S_OUT_OP,
    S_OUT_CL,

    // DI ports 0x14
    SM_EXHAU,
    SM_OUT_OP,
    SM_OUT_CL,
    SM_SIEVE,
    SM_TRANS_4,
    SM_VIBRO_FILTR,
    //x14p07 = "free 2";
    //x14p08 = "free 3";
    //x14p09 = "free 4";
    //x14p10 = "free 5";
    //x14p11 = "free 6";
    //x14p12 = "free 7";
    //x14p13 = "free 8";
    //x14p14 = "free 9";
    //x14p15 = "free 10";

    // DOS ports 0x31
    VALVE_1,
    VALVE_2,
    VALVE_3,
    VALVE_4,
    VALVE_START,
    M_HYDRO,
    M_RAW,
    M_TRANS_1,

    // DOS ports 0x32
    M_SEP,
    M_TRANS_2,
    M_GATE_1,
    M_FU_TR,
    M_FU_ON,
    M_AIR1_ON,
    M_AIR2_ON,
    M_AIR3_ON,

    // DOS ports 0x33
    М_FU_HAYM,
    M_DRDR_ON,
    M_CYCL_ON,
    M_GATE_2,
    M_CRUSH,
    M_FAN,
    M_GATE_3,
    //x33p8 = "not used 1";

    // DOS ports 0x34
    M_HAYM,
    M_DISPEN_ON,
    M_FORCE,
    M_GRAN,
    M_TRANS_3,
    M_GATE_4,
    M_EXHAUST,
    M_OUT_OPEN,

    // DOS ports 0x35
    M_OUT_CLOSE,
    М_SIEVE,
    M_TRANS_4,
    M_VIBRO_FILTR
    //x35p5 = "not used 2";
    //x35p6 = "not used 3";
    //x35p7 = "not used 4";
    //x35p8 = "not used 5";
    ,
    R1,R2,R3,R4,R5,R6,R7,R8,
    DC1,DC2,DC3,DC4,DC5,DC6,DC7,DC8,DC9,DC10,DC11,DC12,DC13,DC14,DC15,
    V10_1, V10_2, V10_3
}
