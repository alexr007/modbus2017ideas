package _IDEAS_.jdesign;

import j4core.BIOcore;
import j4core.ChannelsSet;
import constants.ChanName;
import j3wad.chanvalue.ChanValue;

import java.util.HashMap;
import java.util.Map;

import static constants.ChanName.*;
import static j3wad.chanvalue.TypeDO.*;

/**
 * Created by mac on 20.07.2017.
 */
public final class ModSupply extends ModuleAbstract{
    private final BIOcore core;

    public ModSupply(BIOcore core) {
        this.core = core;
    }

    void cycle() {
        //-------------------------------------------------
        ChannelsSet cs = new ChannelsSet(core.channels());
        Map<ChanName, ChanValue> in = cs.read(
            S_CYL1_MIN,
            S_CYL1_MAX,
            S_CYL2_MIN,
            S_CYL2_MAX);
        //-------------------------------------------------

        //-------------------------------------------------
        HashMap<ChanName, ChanValue> out = new HashMap<>();
        out.put(VALVE_1, ChanValue.DO(OFF));
        out.put(VALVE_2, ChanValue.DO(OFF));
        out.put(VALVE_3, ChanValue.DO(OFF));
        out.put(VALVE_4, ChanValue.DO(OFF));
        cs.write(out);
        //-------------------------------------------------
    }
}
