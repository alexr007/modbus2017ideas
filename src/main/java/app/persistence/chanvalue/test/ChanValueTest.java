package app.persistence.chanvalue.test;

import app.persistence.chanvalue.ChanValue;
import app.persistence.chanvalue.TypeDI;
import app.persistence.chanvalue.TypeDO;

import java.util.ArrayList;

/**
 * Created by alexr on 25.04.2017.
 */
public class ChanValueTest {
    public void test2() throws Exception {
        ArrayList<ChanValue> l = new ArrayList<>();
        l.add(new ChanValue.A(1000));
        l.add(new ChanValue.DI(TypeDI.CLOSED));
        l.add(new ChanValue.DO(TypeDO.ON));

        for (ChanValue a : l) {
            switch (a.type()) {
                case A:
                    if (((ChanValue.A)a).get()==1000){
                        System.out.print("=1000=");
                    }
                    System.out.print("A_");
                    break;
                case DI:
                    if (((ChanValue.DI)a).get()== TypeDI.CLOSED){
                        System.out.print("=CLOSED=");
                    }
                    System.out.print("DI_");
                    break;
                case DO:
                    if (((ChanValue.DO)a).get()== TypeDO.ON){
                        System.out.print("=ON=");
                    }
                    System.out.print("DO_");
                    break;
            }
            System.out.println(a);
        }
    }
}
