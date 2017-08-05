package jwad.channels;

import jbus.modbus.MbRequest;
import jbus.modbus.MbResponse;
import jbus.modbus.ModBusRequestBuilder;
import jbus.modbus.response.MappedTo;
import jbus.modbus.response.Values;
import jbus.modbus.response.ValuesMapped;
import jssc.SerialPortException;
import jwad.WadDevType;
import jwad.chanvalue.ChanValue;
import jwad.chanvalue.ChanValueFromInt;
import jwad.modules.WadAbstractDevice;

/**
 * Created by alexr on 12.05.2017.
 */
public abstract class WadAbstractChannel {
    /**
     * channel ID on ModBus device
     */
    private final int channelId;
    /**
     * parent ModBus device for current channel
     */
    private final WadAbstractDevice device;
    /**
     * mapper for map int to value for future human using
     */
    private final MappedTo mapper;

    /**
     * @param channel modbus channel id
     * 1..N - mean single channel
     * 0    - mean all channelMap (group operation).
     *        not all functions supports group operation
     * @param device modbus real device
     *
     */
    public WadAbstractChannel(int channel, WadAbstractDevice device) {
        if ((channel<0)||(channel>device.properties().chanCount())) {
            throw new IllegalArgumentException(
                String.format("channel must be 0..%s, given:%s", device.properties().chanCount(), channel));
        }
        this.channelId = channel;
        this.device = device;
        this.mapper = new ChanValueFromInt(this);
    }

    public WadDevType type() {
        return device.type();
    }

    public int channel() {
        return channelId;
    }

    public WadAbstractDevice device() {
        return device;
    }

    abstract Values getRaw();

    public  ValuesMapped<ChanValue> get() {
        return new ValuesMapped<ChanValue>(getRaw(), mapper);
    }

    protected ModBusRequestBuilder builder() {
        return device.builder();
    }

    protected MbResponse run(MbRequest request) throws SerialPortException {
        return device.run(request);
    }

    @Override
    public String toString() {
        return String.format("Channel: id:%2s, Device: %s ",channel(),device());
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof WadAbstractChannel
            && this.channelId == WadAbstractChannel.class.cast(obj).channelId
            && this.device.equals(WadAbstractChannel.class.cast(obj).device);
    }
}
