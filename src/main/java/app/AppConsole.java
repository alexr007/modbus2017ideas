package app;

import common.sw.layers.BIOcore;
import jssc.SerialPortException;

/**
 * Created by alexr on 01.05.2017.
 */
public class AppConsole implements Runnable{
    private final BIOcore core;

    public AppConsole(BIOcore core) {
        this.core = core;
    }

    private void finish() {
        try {
            core.finish();
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
            core.devList().forEach(item -> {
                try {
                    System.out.println(core.dev(item).summary());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        finish();
/*
        try {
            ArrayList<ModBusAbstractDevice> list = new ArrayList<ModBusAbstractDevice>(){{
                add(core.dev("DEV11"));
                add(core.dev("DEV21"));
                add(core.dev("DEV31"));
                add(core.dev("DEV41"));
            }};

            list.forEach(item ->
                System.out.println(item.summary())
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
*/
        //new FindComPorts().run();
        //new ModBusDevicesTest().test();
        //new ModBusChannelsTest().test();
        //new ChanValueTest().test2();
        //new DecisionTest().test();
        //new Test_DOS_Timing().run(args[0]);
        //new HashMapTest().test();
        //new ValuesReaderTest().test1();
        //new StringFormat1().print();

/*
        if (args.length < 1 ) {
            System.out.println(new AppDefaultMessage().toString());
            new FindComPorts().run();
        } else {
            new Test_DOS_Timing().run(args[0]);
            //new Test_DOS_31().run(args[0]);
           // new Test_WebServer().main(args);
        }
*/

    }
}
