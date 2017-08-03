package jwad.chanvalue.test;

import jwad.chanvalue.ChanValue;
import jwad.chanvalue.TypeDI;
import jwad.chanvalue.TypeDO;
import org.javatuples.Pair;

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
                new ChanValue.DO(TypeDO.ON)
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
