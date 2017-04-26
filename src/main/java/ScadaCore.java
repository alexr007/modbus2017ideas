import common.sw.layers.test.ModBusDevicesTest;

public class ScadaCore {
    public static void main(String[] args) throws Exception {
        new ModBusDevicesTest().test();
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
