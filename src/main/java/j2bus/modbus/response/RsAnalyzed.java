package j2bus.modbus.response;

import j2bus.modbus.MbResponse;

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

    public int get(int index) throws InvalidModBusResponse {
        return get()[index];
    }
    // contain array iface[] only REAL response;
    public int[] get() throws InvalidModBusResponse {
        int[] ret = new int[]{};
        if (response.has()) {
            RsParsed parsed = new RsParsed(response.get());
            if (parsed.valid())
                if (parsed.device() == rqInfo.deviceId())
                    if (parsed.command() == rqInfo.command())
                        if (parsed.length() == rqInfo.length())
                            ret = parsed.data();
        }
        else
            throw new InvalidModBusResponse("Invalid ModBus Response");

        return ret;
    }

}
