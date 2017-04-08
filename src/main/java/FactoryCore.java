import tests.Test_COMPortList;
import tests.Test_DOS_31;

public class FactoryCore {
    public static void main(String[] args) throws Exception {
        if (args.length < 1 ) {
            System.out.println(String.format("Parameter %1 should be COM Port "));
        } else {
            if ("list".equals(args[0])) {
                new Test_COMPortList().run();
            }
            else {
                new Test_DOS_31().run(args[0]);
            }
        }
    }
}
