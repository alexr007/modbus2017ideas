package app.web;

import app.web.test.ParsedDeviceDataTest;
import com.google.common.base.Joiner;
import common.sw.layers.BIOcore;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.RsText;

import java.io.IOException;

/**
 * Created by alexr on 01.05.2017.
 */
public class TkShowDevice implements Take {
    private final BIOcore core;

    public TkShowDevice(BIOcore core) {
        this.core = core;
    }

    @Override
    public Response act(Request request) throws IOException {
        ParsedDeviceDataTest test = new ParsedDeviceDataTest(request);
        String devSummary = "";
        String query = "";
        try {
            test.testDeviceWrite(core);
            devSummary = test.testDeviceRead(core);
            query = test.testReadQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new RsText(
            //Joiner.on("\n").join(
                devSummary
                //,query)
        );
    }
}
