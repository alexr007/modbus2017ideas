package j2bus.modbus.response;

import j2bus.modbus.command.MbCRC;
import j2bus.modbus.MbResponse;
import java.io.IOException;
import java.util.stream.IntStream;

/**
 * Created by alexr on 21.01.2017.
 */
public class RsParsed {
    private final MbResponse mbResponse;
    private final RqInfo rqInfo;

    private final static int byteID = 0; // 1st byte of response
    private final static int byteCMD = 1; // 2nd byte of response
    private final static int byteLEN = 2; // 3rd byte of response
    private final static int byteDATA = 3; // 4rd byte of response
    public final static int cmdRead = 0x03;
    public final static int cmdWrite = 0x10;

    public RsParsed(MbResponse response, RqInfo rqInfo) {
        this.mbResponse = response;
        this.rqInfo=rqInfo;
    }

    public int[] data() throws IOException {
        final byte[] response=mbResponse.get();
        if (mbResponse.has()
            && new MbCRC(response).valid()                      // packet CRC
            && (response.length == (3+response[byteLEN]+2))  // packet total length
            && response[byteID] == rqInfo.deviceId()         // packet 1st byte - device id
            && response[byteCMD] == rqInfo.command()         // packet 2nd byte - modbus command
            && response[byteLEN] == rqInfo.length() )        // packet 3rd byte - answer length
            {
                return IntStream.range(byteDATA, byteDATA+response[byteLEN])
                    .map(i->response[i])
                    .toArray();
        }
        else {
            throw new IOException("ModBus. Invalid response");
        }
    }
}
