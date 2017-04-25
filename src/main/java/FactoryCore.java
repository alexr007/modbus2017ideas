import common.sw.decision.test.DecisionTest;
import common.sw.persistence.test.PortValueTest;
import tests.some.Test_DOS_Timing;

public class FactoryCore {
    public static void main(String[] args) throws Exception {
        new PortValueTest().test();
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
