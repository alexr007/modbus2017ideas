import app.FindComPorts;
import tests.Test_DOS_31;
import tests.Test_WebServer;

import java.util.Arrays;

public class FactoryCore {
    public static void main(String[] args) throws Exception {
        if (args.length < 1 ) {
            new FindComPorts().run();
        } else {
            new Test_DOS_31().run(args[0]);
           // new Test_WebServer().main(args);
        }
    }
}
