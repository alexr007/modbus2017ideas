package j3wad.chanvalue.test;

import j3wad.chanvalue.ChanValue;
import j3wad.chanvalue.TypeDI;
import j3wad.chanvalue.TypeDO;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by alexr on 25.04.2017.
 */
public class ChanValueTest {
    public void test3() {
       new ArrayList<>(
            Arrays.asList(
                new ChanValue.A(1000),
                new ChanValue.DI(TypeDI.CLOSED),
                new ChanValue.DI(0),
                new ChanValue.DI(1),
                new ChanValue.DI(2),
                new ChanValue.DO(TypeDO.ON),
                new ChanValue.DO(0),
                new ChanValue.DO(1)
            )
        )
           .forEach(
               (ChanValue chanValue) -> {
                   switch (chanValue.type()) {
                       case A:
                           System.out.println("Int:val:"+
                               chanValue.get());
                           break;
                       case DI:
                           System.out.println("DI:val:"+
                               chanValue.get());
                           break;
                       case DO:
                           System.out.println("DO:val:"+
                               chanValue.get());
                           break;
                   }
               }
           );
    }
}
