package j3wad.channels;

import j2bus.modbus.response.MappedTo;
import j2bus.modbus.response.Values;
import j2bus.modbus.response.ValuesMapped;
import j3wad.WadDevType;
import j3wad.chanvalue.ChanValue;
import j3wad.chanvalue.ChanValueFromInt;
import j3wad.modules.WadAbstractDevice;

/**
 * Created by alexr on 12.05.2017.
 */
public abstract class WadAbstractChannel {
    /**
     * channel number on ModBus device
     */
    private final int chanNumber;
    /**
     * parent ModBus device for current channel
     */
    private final WadAbstractDevice device;
    /**
     * mapper for map iface to value for future human using
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
        this.chanNumber = channel;
        this.device = device;
        this.mapper = new ChanValueFromInt(this);
    }

    // just delegate
    public WadDevType type() {
        return device.type();
    }

    public int chanNumber() {
        return chanNumber;
    }

    public WadAbstractDevice device() {
        return device;
    }

    /**
     * get value with Fail Signal mapped to
     * ValuesMapped<ChanValue>
     */
    public  ValuesMapped<ChanValue> get() {
        return new ValuesMapped<ChanValue>(getRaw(), mapper);
    }

    /**
     * get value with Fail Signal
     *
     * single channel / multiple channels
     * this query make 1 query / response for All Channel types AI, DI, AO, DO
     * only DATA retrieved
     *
     * present as abstract in WadAbstractChannel
     * should be implemented in concrete class WAD-xxx-BUS
     */
    public Values getRaw() {
        return getWoFailRaw();
    }

    /**
     * get clean value without Fail Signal mapped to
     * ValuesMapped<ChanValue>
     */
    public  ValuesMapped<ChanValue> getWoFail() {
        return new ValuesMapped<ChanValue>(getWoFailRaw(), mapper);
    }

    /**
     * get clean value without Fail Signal
     * must me implemented in all concrete classes
     */
    public abstract Values getWoFailRaw();

    protected void checkForGroupWrite(int length) {
        if (chanNumber()!=0) {
            throw new IllegalArgumentException(String.format("%s Device: for group Write channel must be 0, given:(%d)",
                device.name(),
                chanNumber));
        }
        if (length!=device.properties().chanCount()) {
            throw new IllegalArgumentException(String.format("%s Device: for group Write channel count must be equals dev.chanCount(%d), given:(%d)",
                device.name(),
                device.properties().chanCount(),
                length));
        }

    }

    public <T extends WadAbstractChannel> T cast() {
        return (T)this;
    }

    @Override
    public String toString() {
        return String.format("Channel: id:%2s, Device: %s ", chanNumber(),device());
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof WadAbstractChannel
            && this.chanNumber == WadAbstractChannel.class.cast(obj).chanNumber
            && this.device.equals(WadAbstractChannel.class.cast(obj).device);
    }
}
