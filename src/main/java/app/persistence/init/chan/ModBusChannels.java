package app.persistence.init.chan;

import app.persistence.init.EnumMapFrom;
import app.persistence.init.dev.ModBusDevices;
import constants.ChanName;
import constants.DevName;
import jbase.hex.HexFromByte;
import jwad.WadDevType;
import jwad.channels.WAD_Channel;
import jssc.SerialPortException;
import jwad.modules.WadAbstractDevice;
import org.javatuples.Pair;
import org.javatuples.Quartet;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * Created by alexr on 15.02.2017.
 */
public final class ModBusChannels {
    private final String ERROR_GET = "Channel Name NotFound:%s";
    private final String ERROR_ADD_K = "Duplicate Channel Name:%s";
    private final String ERROR_ADD_V = "Duplicate Channel, name:%s, at device:%s, modbus id:%s";

    private final ModBusDevices devices;
    private final EnumMap<ChanName, WAD_Channel> channelMap;

    /** ctor 1 - empty set */
    public ModBusChannels(ModBusDevices devices) throws Exception {
        this(devices, new ArrayList<>());
    }

    /** ctor 2 - random quantity Pair<WadAbstractDevice, ChannelList> */
    public ModBusChannels (ModBusDevices devices, Pair<DevName, ChannelList>... channels) throws Exception {
        this(devices, new ArrayList<>(Arrays.asList(channels)));
    }

    /** ctor 3 - ArrayList<Pair<WadAbstractDevice, ChannelList>> */
    public ModBusChannels (ModBusDevices devices, ArrayList<Pair<DevName, ChannelList>> channels) throws Exception {
        this(devices, new ChannelsFromList(devices, channels));
    }

    /** ctor 4 - EnumMapFrom */
    public ModBusChannels(ModBusDevices devices, EnumMapFrom<ChanName, WAD_Channel> channels) throws Exception {
        this(devices, channels.enumMap());
    }

    /** ctor 5 - plain assignment */
    public ModBusChannels(ModBusDevices devices, EnumMap<ChanName, WAD_Channel> channels) {
        this.devices = devices;
        this.channelMap = channels;
    }

    public EnumMap<ChanName, WAD_Channel> channelMap() {
        return channelMap;
    }

    /** try to catch Exception if possible */
    public void add(ChanName channelName, DevName deviceName, int channel) {
        try {
            addVerbose(channelName, deviceName, channel);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void addVerbose(ChanName channelName, DevName deviceName, int channel) throws Exception {
        if (channelMap.containsKey(channelName)) {
            throw new Exception(String.format(ERROR_ADD_K,channelName.toString()));
        }
        WAD_Channel wadChannel = devices.get(deviceName).channel(channel);
        if (channelMap.containsValue(wadChannel)) {
            throw new Exception(
                String.format(ERROR_ADD_V,
                    channelName.toString(),
                    deviceName.toString(),
                    new HexFromByte(channel).toString()
                )
            );
        }
        channelMap.put(channelName, wadChannel);
    }

    /** try to catch Exception if possible */
    public WAD_Channel get(String channelName) {
        try {
            return getVerbose(channelName);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    /** try to catch Exception if possible */
    public WAD_Channel get(ChanName channelName) {
        try {
            return getVerbose(channelName);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public WAD_Channel getVerbose(String channelName) throws Exception {
        return getVerbose(ChanName.valueOf(channelName));
    }

    public WAD_Channel getVerbose(ChanName channelName) throws Exception {
        if (!channelMap.containsKey(channelName)) {
            throw new Exception(String.format(ERROR_GET, channelName));
        }
        return channelMap.get(channelName);
    }

    @Override
    public String toString() {
        return String.format("Channels configured (HashMap):\n%s",
            channelMap.keySet().stream()
                .map(key -> String.format("chan.name: %-14s, dev.name: %s, dev.prop:%s\n",
                    key.toString(), // enum key from HashMap
                    channelMap.get(key).device().toString(), // dev name(AIK, DOS), modbus id
                    channelMap.get(key).device().properties().toString() // dev.prop: signalType, portType, chanCount
                ))
                .collect(Collectors.joining())
        );
    }

    public void finish() throws SerialPortException {
        devices.finish();
    }

    /**
     * channelMap<key,chan>
     *     key - channel name during initialization
     *     chan - channel
     *     chan.device() - device
     *     chan.device().name() - device name (WAD_AIK_BUS, WAD_AO_BUS, WAD_DOS_BUS)
     *     chan.device().type() - device type (AIK, AO, DOS)
     *     chan.device().id() - device modbus id
     *     chan.device().properties() - device properties
     *     chan.device().properties().portType()
     *     chan.device().properties().signalType()
     *     chan.device().properties().chanCount()
     *
     *
     * new HexFromByte(
     *
     */
    public ArrayList<Quartet> quartet() {
        ArrayList<Quartet> list = new ArrayList<>();
        channelMap.forEach((key, chan) -> {
            list.add(new Quartet<ChanName, WadDevType, Integer, CharSequence>(
                key,
                chan.type(),
                chan.channel(),
                new HexFromByte(chan.device().id()).toString()
            ));
        });
        return list;
    }
}
