package jbus.comport;

import com.google.common.base.Joiner;
import jssc.SerialPortList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Pattern;

/**
 * Created by alexr on 02.12.2016.
 */
public class COMPortList {
    private final String PREFIX = ":[";
    private final String POSTFIX = "]";
    private final String DELIM = ", ";
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
        return Joiner.on("").join(count(),PREFIX,Joiner.on(DELIM).join(get()),POSTFIX);
    }

    private void doRead() {
        if (!ready) {
            list.addAll(Arrays.asList(SerialPortList.getPortNames(
                    "/dev/",
                    //"tty.(serial|usbserial|usbmodem).*"
                    Pattern.compile("(tty.*UART*|tty.*serial*)")
            )));
            ready = true;
        }
    }

}
