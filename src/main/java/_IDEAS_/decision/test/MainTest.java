package _IDEAS_.decision.test;

import j4core.BIOcore;
import _IDEAS_.fork.Branch;
import _IDEAS_.fork.Decision;
import _IDEAS_.fork.Solution;
import j1base.hex.HexFromWords;
import j2bus.modbus.response.WordsFrom2Bytes;
import org.javatuples.Quartet;
import java.util.List;

/**
 * Created by alexr on 12.05.2017.
 *
 * /dev/tty.SLAB_USBtoUART
 */
public class MainTest {
    public void test() {
        int i = 0b1111111111111111111111111111111;
        int j = 0;
        int k = i+j;
        System.out.println(k);
    }

    public void test4() {
        int i=1;
        new Decision(
            new Branch(i < 0, () -> System.out.println("<0")),
            new Branch(i == 0, () -> System.out.println("=0")),
            new Branch(i > 0, () -> System.out.println(">0"))
        ).make();
    }

    public void test3() {
        int i=10;
        System.out.println(
            new Solution<>(i > 1,
                "gt 1\n",
                "lt 1\n"
            ).make()
        );
    }

    public void test2() {
        System.out.println(
            new HexFromWords(
                new WordsFrom2Bytes(
                    new int[] {1,1}
                ).get()
            )
        );
    }

    public void test1() throws Exception {
        BIOcore core = new BIOcore(constants.Dv.COM26);
        List<Quartet> list = core.chanListQuartet();
        list.forEach(item -> {
            System.out.println(String.format(
                "Channel_name:%s, Device Type:%s, Channel:%s, ModBus ID:%s",
                item.getValue0().toString(),
                item.getValue1().toString(),
                item.getValue2().toString(),
                item.getValue3().toString()
            ));
        });

    }
}
