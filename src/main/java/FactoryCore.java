import app.FindComPorts;
import app.AppDefaultMessage;
import common.sw.decision.ValuesReaderTest;
import tests.some.Test_DOS_Timing;
import tests.string.StringFormat1;

public class FactoryCore {
    public static void main(String[] args) throws Exception {
        new ValuesReaderTest().test1();
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
