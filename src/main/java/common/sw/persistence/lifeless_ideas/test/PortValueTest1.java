package common.sw.persistence.lifeless_ideas.test;

import common.sw.persistence.TypeDI;
import common.sw.persistence.TypeDO;
import common.sw.persistence.lifeless_ideas.AValue;
import common.sw.persistence.lifeless_ideas.AbtractValue;
import common.sw.persistence.lifeless_ideas.DIValue;
import common.sw.persistence.lifeless_ideas.DOValue;

import java.util.ArrayList;

/**
 * Created by alexr on 25.04.2017.
 */
public class PortValueTest1 {
    public void test1() {
        ArrayList<AbtractValue> l = new ArrayList<>();
        l.add(new AValue(1000));
        l.add(new DIValue(TypeDI.CLOSED));
        l.add(new DOValue(TypeDO.ON));

        for (AbtractValue a : l) {
            switch (a.type()) {
                case A:
                    if (((AValue)a).get()==1000) {};
                    System.out.print("A_");
                    break;
                case DI:
                    if (((DIValue)a).get()==TypeDI.CLOSED) {};
                    System.out.print("DI_");
                    break;
                case DO:
                    if (((DOValue)a).get()==TypeDO.ON) {};
                    System.out.print("DO_");
                    break;
            }
            System.out.println(a);
        }
    }
}