import app.persistence.BIOcore;
import jbase.Branch;
import jbase.Decision;
import jbase.Solution;
import jbase.hex.HexFromByte;
import jbase.hex.HexFromWords;
import jbus.modbus.response.WordsFromBytes;
import org.javatuples.Triplet;
import java.util.ArrayList;

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
                new WordsFromBytes(
                    new int[] {1,1}
                ).get()
            )
        );
    }

    public void test1() throws Exception {
        BIOcore core = new BIOcore(constants.Dv.COM26);
        ArrayList<Triplet> l = core.chanListTriplet();
        l.forEach(item -> {
            System.out.println(String.format(
//                "Channel_name:%s, Device Type:%s, Channel ID:%s",
                "Channel_name:%s, Device Type:%s, Device ModBus ID:%s",
                item.getValue0(),
                item.getValue1(),
//                item.getValue2()
                new HexFromByte((Integer) item.getValue2()).toString()
            ));
        });

    }
}
