package common.sw.layers.test;

import common.hw.modbus.wad.ModBusAbstractDevice;
import common.sw.layers.BIOcore;
import constants.Dv;
import org.xembly.Xembler;

/**
 * Created by alexr on 27.04.2017.
 */
public class BIOcoreTest {
    public void test() throws Exception {
        BIOcore core = new BIOcore(Dv.COM24);
        ModBusAbstractDevice dev = core.devices().get("DEV21");

        System.out.println(
            new Xembler(
                dev.xml()
            ).xml()
        );
        System.out.println(
            dev.summary()
        );

        core.finish();
    }
}
