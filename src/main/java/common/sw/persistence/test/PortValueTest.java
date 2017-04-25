package common.sw.persistence.test;

import common.sw.persistence.*;

import java.util.ArrayList;

/**
 * Created by alexr on 25.04.2017.
 */
public class PortValueTest {
    public void test() {
        //new PortValue.A();
        //new PortValue.D();

        ArrayList<AbtractValue> l = new ArrayList<>();
        l.add(new AValue(1000));
        l.add(new DIValue(DIType.CLOSED));
        l.add(new DOValue(DOType.ON));

        for (AbtractValue a : l) {
            switch (a.type()) {
                case A:
                    System.out.print("A_");
                    break;
                case DI:
                    System.out.print("DI_");
                    break;
                case DO:
                    System.out.print("DO_");
                    break;
            }
            System.out.println(a);
        }
    }
}
