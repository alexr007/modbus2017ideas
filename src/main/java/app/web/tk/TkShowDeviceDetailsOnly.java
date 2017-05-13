package app.web.tk;

import app.persistence.BIOcore;
import app.web.ParsedDeviceData;
import app.web.rs.RsPageDevice;
import jbus.modbus.InvalidModBusFunction;
import jbus.modbus.response.InvalidModBusResponse;
import jssc.SerialPortException;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.RsText;
import org.takes.rs.xe.XeDirectives;

import java.io.IOException;

/**
 * Created by alexr on 13.05.2017.
 */
public class TkShowDeviceDetailsOnly implements Take {
    private final BIOcore core;

    public TkShowDeviceDetailsOnly(BIOcore core) {
        this.core = core;
    }

    @Override
    public Response act(Request request) throws IOException {
        //ParsedDeviceData parsed = new ParsedDeviceData(request);
        Response res;
        try {
            res = new RsPageDevice(
                "/xsl/device.xsl",
                request,
                new XeDirectives( core.dev( "DI1" ).summaryDetailsXml() )
                //new XeDirectives( core.dev( parsed.device() ).summaryDetailsXml() )
            );
        } catch (Exception e) {
            e.printStackTrace();
            res = new RsText("exception");
        }
        return res;
    }
}
