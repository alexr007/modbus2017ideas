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
public class ModBusChannels {
    private final ModBusDevices devices;
    private final EnumMap<ChanName, WAD_Channel> channelMap;

    // ctor 1 - empty set
    public ModBusChannels(ModBusDevices devices) throws Exception {
        this(devices, new ArrayList<>());
    }

    // ctor 2 - random quantity Pair<WadAbstractDevice, ChannelList>
    public ModBusChannels (ModBusDevices devices, Pair<DevName, ChannelList>... channels) throws Exception {
        this(devices, new ArrayList<>(Arrays.asList(channels)));
    }

    // ctor 3 - ArrayList<Pair<WadAbstractDevice, ChannelList>>
    public ModBusChannels (ModBusDevices devices, ArrayList<Pair<DevName, ChannelList>> channels) throws Exception {
        this(devices, new ChannelsFromList(devices, channels));
    }

    // ctor 4 - EnumMapFrom
    public ModBusChannels(ModBusDevices devices, EnumMapFrom<ChanName, WAD_Channel> channels) throws Exception {
        this(devices, channels.enumMap());
    }

    // ctor 5 - plain assignment
    public ModBusChannels(ModBusDevices devices, EnumMap<ChanName, WAD_Channel> channels) {
        this.devices = devices;
        this.channelMap = channels;
    }

    public EnumMap<ChanName, WAD_Channel> channels() {
        return channelMap;
    }

    public void add(ChanName channelName, DevName deviceName, int channel) throws Exception {
        if (channelMap.containsKey(channelName)) {
            throw new Exception(String.format("Duplicate Channel Name:%s",channelName.toString()));
        }
        WAD_Channel wadChannel = devices.get(deviceName).channel(channel);
        if (channelMap.containsValue(wadChannel)) {
            throw new Exception(
                String.format("Duplicate Channel, name:%s, at device:%s, modbus id:%s",
                    channelName.toString(),
                    deviceName.toString(),
                    new HexFromByte(channel).toString()
                )
            );
        }
        channelMap.put(channelName, wadChannel);
    }

    public WAD_Channel get(ChanName channelName) {
        try {
            return get(channelName);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Channel Name NotFound:%s",channelName),e);
        }
    }

    /**
     * Verbose version get(ChanName channelName)
     * @param channelName
     * @return
     * @throws Exception
     */
    public WAD_Channel getVerbosed(ChanName channelName) throws Exception {
        if (!channelMap.containsKey(channelName)) {
            throw new Exception(String.format("Channel Name NotFound:%s",channelName));
        }
        return channelMap.get(channelName);
    }

    public WAD_Channel get(String channelName) throws Exception {
        ChanName cname= ChanName.valueOf(channelName);
        if (!channelMap.containsKey(cname)) {
            throw new Exception(String.format("Channel Name NotFound:%s",channelName));
        }
        return channelMap.get(cname);
    }
    /////////////////////////////////////////////////////////////
/**
 *
 * Find all Channels on same device
 *
 * @param chan ChanName
 * @return EnumSet<ChanName>
 */

    public Set<ChanName> getAllChannelFromSameDevice(ChanName chan) {
        // device Id just for more verbose code while learning streams
        int deviceId = channelMap.get(chan).device().id();
        return
            // Really don't know about real difference between Set<ChanName> and EnumSet<ChanName>
            // TODO: need to real benchmark later
            EnumSet.copyOf(
                channelMap.entrySet().stream()
                    .filter(entry -> entry.getValue().device().id() == deviceId)
                    .map(entry -> entry.getKey())
                    .collect(Collectors.toSet())
            );
    }

    /**
     *
     * converts: Set<ChanName> to Set<WadAbstractDevice>
     * for future purposes
     * @param chanSet Set<ChanName>
     * @return Set<WadAbstractDevice>
     */
    public Set<WadAbstractDevice> getDeviceSet(Set<ChanName> chanSet) {
        return chanSet.stream()
            .map(c -> channelMap.get(c).device())
            .distinct()
            .collect(Collectors.toSet());
    }

    /**
     * converts: Set<ChanName> to Set<DeviceId, UsedChanCount>
     * @param chanSet Set<ChanName>
     * @return Map<Integer, Long> means Set<DeviceId, UsedChanCount>
     */
    public Map<Integer, Long> getMapDeviceChanCount(Set<ChanName> chanSet) {
        return chanSet.stream()
            .map(key -> channelMap.get(key).device()) // map chanId -> device
            .collect(
                Collectors.groupingBy(device -> device.id(), // map device -> device id
                    Collectors.counting() // count devices with same id
                )
            );
    }

    public Map<Integer, Set<Integer>> getMapDeviceChanList (Set<ChanName> chanSet) {
        return chanSet.stream()
            .map(key -> channelMap.get(key)) // map chanId -> channel
            // now mapped to chan:
            // chan.device().id() - modbus device id
            // chan.channel() - channel id on device
            .collect(
                Collectors.groupingBy(new Function<WAD_Channel, Integer>() {
                                          @Override
                                          public Integer apply(WAD_Channel chan) {
                                              return chan.device().id();
                                          }
                                      },
                    Collectors.mapping(new Function<WAD_Channel, Integer>() {
                                           @Override
                                           public Integer apply(WAD_Channel chan) {
                                               return chan.channel();
                                           }
                                       },
                        Collectors.toSet())

                )
            );
    }
    /////////////////////////////////////////////////////////////

    @Override
    public String toString() {
        return String.format("Channels configured (HashMap):\n%s",
            channelMap.keySet().stream()
                .map(key -> String.format("chan.name: %s, dev.name: %s, dev.prop:%s\n",
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
