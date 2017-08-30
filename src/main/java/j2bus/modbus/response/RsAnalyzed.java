package j2bus.modbus.response;

import j2bus.modbus.MbResponse;

import java.io.IOException;

/**
 * Created by alexr on 22.01.2017.
 *
 * Response Analyzed
 * with RsParsed
 */
public class RsAnalyzed {
    private final MbResponse response;
    private final RqInfo rqInfo;

    public RsAnalyzed(MbResponse response, RqInfo rqInfo) {
        this.response = response;
        this.rqInfo = rqInfo;
    }

    public int get(int index)  {
        return get()[index];
    }

    public int[] get()  {
        return new RsParsed(response, rqInfo).data();
    }

}
