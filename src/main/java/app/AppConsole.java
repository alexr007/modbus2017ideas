package app;

import j4core.BIOcore;

/**
 * Created by alexr on 01.05.2017.
 */
public class AppConsole implements Runnable{
    private final BIOcore core;

    public AppConsole(BIOcore core) {
        this.core = core;
    }

    private void finish() {
       core.finish();
    }

    @Override
    public void run() {
/*
        RqFake fake = new RqFake(
            Arrays.asList(
                "GET /hello?a=3&b=7&c&d=9%28x%29&ff",
                "Host: a.example.com",
                "Content-type: text/xml"
            ),
            ""
        );
        try {
            String d = new RqHref.Base(fake).href().param("d").iterator().next();
            String c = new RqHref.Base(fake).href().param("c").iterator().next();
            String e = new RqHref.Base(fake).href().param("ff").iterator().next();
            System.out.println(c);
            System.out.println(d);
            System.out.println(e);
            //d.forEach(i-> System.out.println(i));
        } catch (IOException e) {
            e.printStackTrace();
        }
*/

/*
        System.out.println(new COMPortList());
            j4core.devSet().forEach(item -> {
                    System.out.println(j4core.dev(item).summaryTxt());
                }
            );
*/
        //finish();
/*
        try {
            ArrayList<WadAbstractDevice> set = new ArrayList<WadAbstractDevice>(){{
                add(j4core.dev("DEV11"));
                add(j4core.dev("DEV21"));
                add(j4core.dev("DEV31"));
                add(j4core.dev("DEV41"));
            }};

            set.forEach(item ->
                System.out.println(item.summaryTxt())
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
*/
        //new AppFindComPorts().run();
        //new ModBusDevicesTest().core_test();
        //new ModBusChannelsTest().core_test();
        //new ChanValueTest().test2();
        //new DecisionTest().core_test();
        //new AppConsoleSpeedTest().run(args[0]);
        //new HashMapTest().core_test();
        //new ValuesReaderTest().test1();
        //new StringFormat1().print();

/*
        if (args.length < 1 ) {
            System.out.println(new AppDefaultMessage().toString());
            new AppFindComPorts().run();
        } else {
            new AppConsoleSpeedTest().run(args[0]);
            //new Test_DOS_31().run(args[0]);
           // new Test_WebServer().main(args);
        }
*/

    }
}
