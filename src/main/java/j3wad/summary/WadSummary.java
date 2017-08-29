package j3wad.summary;

import jssc.SerialPortException;
import org.xembly.Directives;

import java.io.IOException;

/**
 * Created by alexr on 28.04.2017.
 */
public interface WadSummary {
    Directives xmlDir() throws IOException;
    String txt() throws IOException;
}
