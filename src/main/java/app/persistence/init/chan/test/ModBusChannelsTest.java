package app.persistence.init.chan.test;

import app.persistence.BIOcore;
import app.persistence.init.chan.ChanSet;
import constants.ChanName;
import jwad.channels.WAD_Channel;
import org.javatuples.Pair;

import java.util.EnumSet;

public class ModBusChannelsTest {
    public void test1 (BIOcore core) {
        // output should be similar
        System.out.println(
            core.channels().get(ChanName.M_VIBRO_FILTR)
                .equals(
                    core.channels().get("M_VIBRO_FILTR")
                )
        );
        System.out.println(
            core.channels().get(ChanName.M_VIBRO_FILTR)
                .equals(
                    core.channels().get(new Pair<>(0x35,4))
                )
        );
        System.out.println(
            core.channels().get(ChanName.M_VIBRO_FILTR)
                .equals(
                    core.channels().get(0x35,4)
                )
        );
        // output should be similar
        System.out.println(
            core.channels().get(0x31,1).toString()
        );
        System.out.println(
            core.channels().get(new Pair(0x31,1)).toString()
        );
        System.out.println(
            core.channels().get("VALVE_1").toString()
        );
        System.out.println(
            core.channels().get(ChanName.VALVE_1).toString()
        );
        // output should be similar
        WAD_Channel c = core.channels().get(0x31, 1);
        System.out.println(
            core.channels().getName(c)
        );
        System.out.println(
            core.channels().getName(0x31, 1)
        );
        System.out.println(
            core.channels().getName(new Pair(0x31,1))
        );
        System.out.println(
            new ChanSet(core.channels(), EnumSet.allOf(ChanName.class)).values1()
        );
    }

}
