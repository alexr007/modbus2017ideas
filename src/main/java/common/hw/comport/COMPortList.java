package common.hw.comport;

import com.google.common.base.Joiner;
import jssc.SerialPortList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by alexr on 02.12.2016.
 */
public class COMPortList {
    private final String PREFIX = ":[";
    private final String POSTFIX = "]";
    private boolean ready = false;
    private final Collection<CharSequence> list = new ArrayList<>();

    public Collection<CharSequence> get()
    {
        doRead();
        return list;
    }

    public int count()
    {
        doRead();
        return list.size();
    }

    public String toString() {
        return Joiner.on(", ").join(
            count(),PREFIX,get(),POSTFIX);
    }

    private void doRead() {
        if (!ready) {
            list.addAll(Arrays.asList(SerialPortList.getPortNames()));
            ready = true;
        }
    }

}
