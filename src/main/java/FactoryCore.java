import app.FindComPorts;
import app.AppDefaultMessage;
import common.hw.modbus.response.Values;
import common.sw.decision.*;
import tests.some.Test_DOS_Timing;
import tests.string.StringFormat1;

import java.util.Arrays;
import java.util.HashMap;

public class FactoryCore {
    public static void main(String[] args) throws Exception {
        new DecisionTest().test();
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
