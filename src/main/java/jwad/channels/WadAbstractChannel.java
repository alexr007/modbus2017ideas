package jwad.channels;

import jbus.modbus.MbRequest;
import jbus.modbus.MbResponse;
import jbus.modbus.ModBusRequestBuilder;
import jssc.SerialPortException;
import jwad.WadDevType;
import jwad.modules.WadAbstractDevice;

/**
 * Created by alexr on 12.05.2017.
 */
public class WadAbstractChannel {
    private final int channel;
    private final WadAbstractDevice device;

    /**
     * @param channel modbus channel id
     * aa..N - mean single channel
     * 0    - mean all channels (group operation).
     *        not all functions supports group operation
     * @param device modbus real device
     *
     */
    public WadAbstractChannel(int channel, WadAbstractDevice device) {
        if ((channel<0)||(channel>device.properties().chanCount())) {
            throw new IllegalArgumentException(
                String.format("channel must be 0..%s, given:%s", device.properties().chanCount(), channel));
        }
        this.channel = channel;
        this.device = device;
    }

    public WadDevType type() {
        return device.type();
    }

    public int channel() {
        return channel;
    }

    public WadAbstractDevice device() {
        return device;
    }

    protected ModBusRequestBuilder builder() {
        return device.builder();
    }

    protected MbResponse run(MbRequest request) throws SerialPortException {
        return device.run(request);
    }
}