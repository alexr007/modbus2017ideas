package app.persistence.init.chan;

import constants.ChanName;
import jwad.chanvalue.ChanValue;
import jwad.modules.WadAbstractDevice;
import org.javatuples.Pair;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChanSet {
    private final ModBusChannels modBusChannels;
    private final Set<ChanName> chanSet;

    public ChanSet(ModBusChannels modBusChannels) {
        this.modBusChannels = modBusChannels;
        this.chanSet = Collections.emptySet();
    }

    public ChanSet(ModBusChannels modBusChannels, Set<ChanName> chanSet) {
        this.modBusChannels = modBusChannels;
        this.chanSet = chanSet;
    }

    /**
     *
     * Find all Channels on same device
     *
     * @param chan ChanName
     * @return EnumSet<ChanName>
     */
    public Set<ChanName> getAllChannelFromSameDevice(ChanName chan) {
        // device Id just for more verbose code while learning streams
        int deviceId = modBusChannels.channelMap().get(chan).device().id();
        return
            // Really don'tests.t know about real difference between Set<ChanName> and EnumSet<ChanName>
            // TODO: need to real benchmark later
            EnumSet.copyOf(
                modBusChannels.channelMap().entrySet().stream()
                    .filter(entry -> entry.getValue().device().id() == deviceId)
                    .map(entry -> entry.getKey())
                    .collect(Collectors.toSet())
            );
    }

    /**
     * converts: Set<ChanName> to Set<WadAbstractDevice>
     * @return Set<WadAbstractDevice>
     */
    public Set<WadAbstractDevice> getDeviceSet() {
        return chanSet.stream()
            .map(c -> modBusChannels.channelMap().get(c).device())
            .distinct()
            .collect(Collectors.toSet());
    }

    /**
     * converts: Set<ChanName> to Set<DeviceId, UsedChanCount>
     * @return Map<Integer, Long> means Set<DeviceId, UsedChanCount>
     */
    public Map<Integer, Long> getMapDeviceChanCount() {
        return chanSet.stream()
            .map(key -> modBusChannels.channelMap().get(key).device()) // map chanId -> device
            .collect(
                Collectors.groupingBy(device -> device.id(), // map device -> device id
                    Collectors.counting() // count devices with same id
                )
            );
    }

    /**
     * converts: Set<ChanName> to Set<Dev, Set<Channels>>
     * @return Map<Integer, Set<Integer>> mean Set<Dev, Set<Channels>>
     */
    public Map<Integer, Set<Integer>> getMapDeviceChanList () {
        return chanSet.stream()
            .map(key -> modBusChannels.channelMap().get(key)) // map chanId -> channel
            .collect(
                Collectors.groupingBy(chan -> chan.device().id(),
                    Collectors.mapping(chan -> chan.channel(),
                        Collectors.toSet())
                )
            );
    }

    public Map<Integer, WadAbstractDevice> devMap() {
        return chanSet.stream()
            .map(chanName -> {
                WadAbstractDevice d = modBusChannels.channelMap().get(chanName).device();
                return new Pair<>(d.id(), d);
            })
            .distinct()
            .collect(Collectors.toMap(
                Pair::getValue0,
                Pair::getValue1)
            );
    }

    /**
     * Writes EnumMap<ChanName, ChanValue> to device
     * @param mapToWrite
     */
    void write(EnumMap<ChanName, ChanValue> mapToWrite) {

    }

    /**
     * reads values from Device to set
     * @return
     */
    Map<ChanName, ChanValue> values() {
        Map<Integer, Set<Integer>> devChan = getMapDeviceChanList();
        Map<Integer, WadAbstractDevice> devMap = devMap();
        EnumMap<ChanName, ChanValue> result = new EnumMap<>(ChanName.class);

        Map<Integer, List<Integer>> collect = devChan.keySet().stream()
            .map(devId -> new Pair<>(devId, devMap.get(devId).channel(0).get().list())) // это мы прочитали устройство
            .collect(Collectors.toMap(
                Pair::getValue0,
                Pair::getValue1
            ));
        return result;
    }
}
