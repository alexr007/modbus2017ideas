import app.persistence.BIOcore;
import jbase.hex.IntAsHex;
import org.javatuples.Triplet;

import java.util.ArrayList;


/**
 * Created by alexr on 12.05.2017.
 */
public class MainTest {
    public void test() throws Exception {
        BIOcore core = new BIOcore(constants.Dv.COM26);
        ArrayList<Triplet> l = core.chanListTriplet();
        l.forEach(item -> {
            System.out.println(String.format(
//                "Channel_name:%s, Device Type:%s, Channel ID:%s",
                "Channel_name:%s, Device Type:%s, Device ModBus ID:%s",
                item.getValue0(),
                item.getValue1(),
//                item.getValue2()
                new IntAsHex((Integer) item.getValue2()).toString()
            ));
        });

    }
}
