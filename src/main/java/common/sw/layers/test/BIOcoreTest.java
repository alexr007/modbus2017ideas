package common.sw.layers.test;

import common.sw.layers.BIOcore;
import constants.Dv;
import org.xembly.Xembler;

/**
 * Created by alexr on 27.04.2017.
 */
public class BIOcoreTest {
    public void test() throws Exception {
        BIOcore core = new BIOcore(Dv.COM24);
//        core.devices().get("DEV1").xml();

        System.out.println(
            new Xembler(
                core.devices().get("DEV1").xml()
            ).xml()
        );
        System.out.println(
                core.devices().get("DEV1").summary()
        );

        core.finish();
    }
}
