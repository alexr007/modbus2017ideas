package common.sw.layers.test;

import jwad.ModBusAbstractDevice;
import common.sw.layers.BIOcore;
import constants.Dv;

import java.util.ArrayList;

/**
 * Created by alexr on 27.04.2017.
 */
public class BIOcoreTest {
    public void test() throws Exception {
        BIOcore core = new BIOcore(Dv.COM26);

        ArrayList<ModBusAbstractDevice> list = new ArrayList<ModBusAbstractDevice>(){{
            add(core.dev("DEV11"));
            add(core.dev("DEV21"));
            add(core.dev("DEV31"));
            add(core.dev("DEV41"));
        }};

/*
        list.forEach(item ->
            System.out.println(item.xml())
        );
*/

/*
        list.forEach(item ->
                System.out.println(
                    new Xembler(
                    item.xml()
                    ).xmlQuietly()
                )
        );
*/
        System.out.println(core.chan("CH211").device().toString());
        System.out.println(core.chan("CH212").device().toString());
/*
        core.chan("CH1").get();
        core.chan("CH1").set(0);
        core.chan("CH1").on();
        core.chan("CH1").off();
*/

        core.finish();
    }
}
