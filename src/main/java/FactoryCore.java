import tests.Test_COMPortList;
import tests.Test_DOS_31;
import tests.Test_WebServer;

public class FactoryCore {
    public static void main(String[] args) throws Exception {
        new Test_WebServer().main(args);

/*
        if (args.length < 1 ) {
            new Test_COMPortList().run();
        } else {
            new Test_DOS_31().run(args[0]);
        }
*/
    }
}
