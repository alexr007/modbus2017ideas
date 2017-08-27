package j2bus.comport;

import j1base.IterableToString;
import jssc.SerialPortList;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by alexr on 02.12.2016.
 */
public class COMPortList {
    private final String PREFIX = ":[";
    private final String POSTFIX = "]";
    private final String DELIM = ", ";

    //@Timeable(limit = 500, unit = TimeUnit.MILLISECONDS)
    public Collection<CharSequence> get()
    {
        return Arrays.stream(SerialPortList.getPortNames(
            "/dev/",
            //"tty.(serial|usbserial|usbmodem).*"
            Pattern.compile("(tty.*UART*|tty.*serial*)")
            ))
            .collect(Collectors.toList());
    }

    public String toString() {
        return new IterableToString<>(get()).toString();
    }
}
