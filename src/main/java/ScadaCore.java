import app.*;
import app.decision.test.SortedSetTest;
import app.persistence.BIOcore;
import constants.ChanName;
import constants.Dv;
import jwad.channels.WAD_Channel;
import t.t1;

import java.util.ArrayList;
import java.util.EnumSet;

public class ScadaCore {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            new FindComPorts().run();
            //new app.decision.test.MainTest().test();
            //new t1().run();
        } else
        if ((args.length == 2)&&(args[1].equals("test"))) {
            new AppConsoleSpeedTest(args[0]).run();
        } else {
            SortedSetTest.test2();

/*
            BIOcore core = new BIOcore(args[0],true);
            WAD_Channel chan = core.chan(ChanName.S_A_CRUSH);
            System.out.println(
                core.channels().getAllFromSameDevice(ChanName.SM_AIR1)
            );
            System.out.println(
                core.channels().getAllFromSameDevice(ChanName.S_A_CRUSH)
            );
            System.out.println(
                core.channels().getAllFromSameDevice(ChanName.M_AIR1)
            );
            System.out.println(core.devices().toString());
            System.out.println(core.channels().toString());
*/

            //BIOcore core = new BIOcore(Dv.COM26);

/*
            new ArrayList<Runnable>() {{
                // создаем массив Runnable
                add(new AppConsole(core));
                //add(new AppDecision(core));
                add(new AppWebServer(core));

                // массив Thread
                ArrayList<Thread> th = new ArrayList<Thread>();
                // добавляем все Runnable -> Thread
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
*/
        }
    }
    // todo timeout while send packed to module;
}
