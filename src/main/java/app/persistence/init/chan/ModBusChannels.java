package app.persistence.init.chan;

import app.persistence.init.EnumMapFrom;
import app.persistence.init.dev.ModBusDevices;
import constants.ChanName;
import constants.DevName;
import jbase.hex.HexFromByte;
import jwad.WadDevType;
import jwad.channels.WAD_Channel;
import jssc.SerialPortException;
import org.javatuples.Pair;
import org.javatuples.Quartet;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by alexr on 15.02.2017.
 */
public final class ModBusChannels {
    private final String ERROR_GET = "Channel Name NotFound:%s";
    private final String ERROR_ADD_K = "Duplicate Channel Name:%s";
    private final String ERROR_ADD_V = "Duplicate Channel, name:%s, at device:%s, modbus id:%s";

    private final ModBusDevices devices;
    /** Map to hold pairs <ChanName, WAD_Channel> */
    private final EnumMap<ChanName, WAD_Channel> channelMap;

    /** Map to hold pairs <pair<dev,chan>, WAD_Channel> */
    private final Map<Pair<Integer,Integer>, WAD_Channel> channelMapByDevChan = new HashMap<>();

    /** Map to hold pairs <pair<dev,chan>, ChanName> */
    private final Map<Pair<Integer,Integer>, ChanName> channelMapPairChan = new HashMap<>();

    /** Map to hold pairs <WAD_Channel, ChanName> */
    private final Map<WAD_Channel, ChanName> channelMapChanName = new HashMap<>();

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
        this.channelMap.forEach(
            (chanName, wad_channel) -> {
                Pair<Integer, Integer> pair = new Pair<>(wad_channel.device().id(), wad_channel.chanNumber());
                // fill the channelMapByDevChan
                channelMapByDevChan.put(pair, wad_channel);
                // fill the channelMapPairChan
                channelMapPairChan.put(pair, chanName);
                // fill the channelMapChanName
                channelMapChanName.put(wad_channel, chanName);
            }
        );
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

    public Pair<Integer, Integer> getDC(ChanName channelName) {
        WAD_Channel channel = get(channelName);
        return new Pair<>(channel.device().id(),channel.chanNumber());
    }

    /** try to catch Exception if possible */
    public ChanName getName(WAD_Channel channel)  {
        try {
            return getNameVerbose(channel);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public ChanName getName(int dev, int chan)  {
        try {
            return getNameVerbose(dev, chan);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public ChanName getName(Pair<Integer, Integer> pair)  {
        try {
            return getNameVerbose(pair);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    /** try to catch Exception if possible */
    public WAD_Channel get(int devId, int chanId) {
        try {
            return getVerbose(devId, chanId);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public WAD_Channel get(Pair<Integer, Integer> pair) {
        try {
            return getVerbose(pair);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public WAD_Channel get(String channelName) {
        try {
            return getVerbose(channelName);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public WAD_Channel get(ChanName channelName) {
        try {
            return getVerbose(channelName);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    /** Verbose version */
    public ChanName getNameVerbose(WAD_Channel channel) throws Exception {
        if (!channelMapChanName.containsKey(channel)) {
            throw new Exception(String.format(ERROR_GET,
                String.format("dev:%s, chan:%d", new HexFromByte(channel.device().id()).toString(), channel.chanNumber())));
        }
        return channelMapChanName.get(channel);
    }

    public ChanName getNameVerbose(int dev, int chan) throws Exception {
        return getNameVerbose(new Pair<>(dev, chan));
    }

    public ChanName getNameVerbose(Pair<Integer, Integer> pair) throws Exception {
        if (!channelMapPairChan.containsKey(pair)) {
            throw new Exception(String.format(ERROR_GET,
                String.format("dev:%s, chan:%d", new HexFromByte(pair.getValue0()).toString(), pair.getValue1())));
        }
        return channelMapPairChan.get(pair);
    }

    /** Verbose version */
    public WAD_Channel getVerbose(int devId, int chanId) throws Exception {
        return getVerbose(new Pair<>(devId, chanId));
    }

    public WAD_Channel getVerbose(Pair<Integer, Integer> pair) throws Exception {
        if (!channelMapByDevChan.containsKey(pair)) {
            throw new Exception(String.format(ERROR_GET,
                String.format("dev:%s, chan:%d", new HexFromByte(pair.getValue0()).toString(), pair.getValue1())));
        }
        return channelMapByDevChan.get(pair);
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
            channelMap.entrySet().stream()
                .map(ent -> String.format("chan.name: %-14s, chan.id:%2s, dev.name: %s, dev.prop:%s\n",
                    ent.getKey().toString(), // enum key from HashMap
                    ent.getValue().chanNumber(), // chan id
                    ent.getValue().device().toString(), // dev name(AIK, DOS), modbus id
                    ent.getValue().device().properties().toString() // dev.prop: signalType, portType, chanCount
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
                chan.chanNumber(),
                new HexFromByte(chan.device().id()).toString()
            ));
        });
        return list;
    }
}
