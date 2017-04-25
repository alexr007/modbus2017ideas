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

        AValue aValue = new AValue(1000);
        DIValue diValue = new DIValue(DIType.CLOSED);
        DOValue doValue = new DOValue(DOType.ON);

        ArrayList<AbtractValue> l = new ArrayList<>();
        l.add(aValue);
        l.add(diValue);
        l.add(doValue);

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
