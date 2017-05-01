import app.AppConsole;
import app.AppDecision;
import app.AppWebServer;
import app.FindComPorts;
import common.Timed;
import common.sw.layers.BIOcore;
import common.sw.layers.test.BIOcoreTest;
import common.sw.layers.test.ModBusChannelsTest;
import common.sw.layers.test.ModBusDevicesTest;
import constants.Dv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ScadaCore {
    public static void main(String[] args) throws Exception {
        BIOcore core = new BIOcore(Dv.COM26);

        new ArrayList<Runnable>() {{
            add(new AppConsole(core));
            add(new AppDecision(core));
            add(new AppWebServer(core));
            ArrayList<Thread> th = new ArrayList<Thread>();
            forEach(r -> th.add(new Thread(r)));
            th.forEach(t -> t.start());
            System.out.println("All threads started");
            th.forEach(t -> {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            System.out.println("All threads finished");
        }};

    }
}
