import app.*;
import app.persistence.BIOcore;
import constants.Dv;

import java.util.ArrayList;

public class ScadaCore {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            //new FindComPorts().run();
            new MainTest().test();
        } else
        if ((args.length == 2)&&(args[1].equals("test"))) {
            new AppConsoleSpeedTest(args[0]).run();
        } else {
            BIOcore core = new BIOcore(args[0]);
            //BIOcore core = new BIOcore(Dv.COM26);

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
        }
    }
    // todo timeout while send packed to module;
}
