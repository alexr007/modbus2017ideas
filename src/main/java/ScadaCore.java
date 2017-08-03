import app.*;
import app.persistence.BIOcore;
import app.persistence.init.chan.ChanSet;
import app.persistence.init.chan.test.ModBusChannelsTest;
import constants.ChanName;
import jwad.chanvalue.test.ChanValueTest;
import org.javatuples.Pair;

import java.util.EnumSet;

public class ScadaCore {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            new FindComPorts().run();
            //new app.decision.core_test.MainTest().core_test();
            //new t1().run();
        } else
        if ((args.length == 2)&&(args[1].equals("core_test"))) {
            new AppConsoleSpeedTest(args[0]).run();
        } else {
            //SortedSetTest.test33();
            new ChanValueTest().test3();
            //new rxTest1().test0();

            BIOcore core = new BIOcore(args[0]);
            ChanSet cs = new ChanSet(core.channels(), EnumSet.allOf(ChanName.class));

            System.out.println( cs.getAllChannelFromSameDevice(ChanName.SM_AIR1) );
            System.out.println( cs.getAllChannelFromSameDevice(ChanName.SM_GATE_4) );
            System.out.println( cs.getDeviceSet() );
            System.out.println( cs.getMapDeviceChanCount() );
            System.out.println( cs.getMapDeviceChanList()  );

            System.out.println(core.devices().toString());
            System.out.println(core.channels().toString());

            new ModBusChannelsTest().test1(core);

/*
            WAD_Channel chan = core.chan(ChanName.S_A_CRUSH);
            System.out.println(
                core.channelMap().getAllChannelFromSameDevice(ChanName.S_A_CRUSH)
            );
            System.out.println(
                core.channelMap().getAllChannelFromSameDevice(ChanName.M_AIR1)
            );
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
                th.forEach(tests.t -> tests.t.start());
                System.out.println("All threads started");
                th.forEach(tests.t -> {
                    try {
                        tests.t.join();
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
