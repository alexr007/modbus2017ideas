package app.persistence.init.chan;

import constants.ChanName;
import jwad.chanvalue.ChanValue;
import jwad.modules.WadAbstractDevice;
import org.javatuples.Pair;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ChanSet {
    private final ModBusChannels modBusChannels;
    private final Set<ChanName> chanSet;

    public ChanSet(ModBusChannels modBusChannels) {
        this.modBusChannels = modBusChannels;
        this.chanSet = Collections.emptySet();
    }

    public ChanSet(ModBusChannels modBusChannels, EnumMap<ChanName, ChanValue> chanSet) {
        this(modBusChannels, new HashSet<>(chanSet.keySet()));
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
     * @return Map<Integer, Set<Integer>> mean Map<Dev, Set<Channels>>
     */
    public Map<Integer, Set<Integer>> getMapDeviceChanList () {
        return chanSet.stream()
            .map(key -> modBusChannels.channelMap().get(key)) // map chanId -> channel
            .collect(
                Collectors.groupingBy(chan -> chan.device().id(),
                    Collectors.mapping(chan -> chan.chanNumber(),
                        Collectors.toSet())
                )
            );
    }

    /**
     * converts: Set<ChanName> to Map<Integer, WadAbstractDevice>
     * @return Map<Integer, WadAbstractDevice> mean Map<devId, WadAbstractDevice>
     */
    public Map<Integer, WadAbstractDevice> devMap() {
        return chanSet.stream()
            .map(chanName -> modBusChannels.channelMap().get(chanName).device())
            .distinct()
            .collect(Collectors.toMap(
                dev -> dev.id(),
                dev -> dev
            ));
    }

    public Set<Pair<Integer, Integer>>setPairDC() {
        return chanSet.stream()
            .map(item -> modBusChannels.getDC(item))
            .collect(Collectors.toSet());
    }

    private void checkIdentically(EnumMap<ChanName, ChanValue> mapToWrite) {
        // check origin Set equals mapToWrite.set
        Set<ChanName> toWrite = new HashSet<>(mapToWrite.keySet());
        if (! chanSet.equals(toWrite)) {
            throw new IllegalArgumentException(String.format(
                "Origin set and set to write is different\nOrigin: %s\nToWrite: %s\n",
                chanSet, toWrite));
        }
    }

    /**
     * Writes EnumMap<ChanName, ChanValue> to device
     * @param mapToWrite
     */
    public void write(EnumMap<ChanName, ChanValue> mapToWrite) {
        checkIdentically(mapToWrite);

        Map<Integer, Set<Integer>> mapDeviceChanList = getMapDeviceChanList();
        Map<Integer, WadAbstractDevice> devMap = devMap();

        mapDeviceChanList.entrySet().stream()
            .forEach(ent -> {
                WadAbstractDevice dev = devMap.get(ent.getKey());
                Set<Integer> set = ent.getValue();
                int setSize = set.size();
                if (setSize==1) {
                    System.out.println("writting 1 channel");
                } else if (setSize==dev.properties().chanCount()) {
                    System.out.println(String.format("writting ALL %s channels", dev.properties().chanCount()));
                } else {
                    System.out.println(String.format("writting %s of %s channels", setSize, dev.properties().chanCount()));
                }
            });
    }

    public Map<ChanName, Integer> values0_work_ok() {
        Map<Integer, WadAbstractDevice> devMap = devMap();
        Set<Pair<Integer, Integer>> setPairDC = setPairDC();
        // Map<Dev, Set<Channels>>
        return getMapDeviceChanList().keySet().stream() // Set<Dev>
            .collect(Collectors.toMap(
                key -> key,
                // this is real reading
                //key -> devMap.bytes(key).channel(0).bytes().list())
                // this is fake reader
                key -> IntStream.rangeClosed(1, devMap.get(key).properties().chanCount()).boxed().collect(Collectors.toList())
            ))
            // Map<Dev, List<Values>>
            .entrySet()
            // Set<Dev>
            .stream()
            .map(ent -> IntStream.range(0, ent.getValue().size())
                .mapToObj(index -> new AbstractMap.SimpleEntry<>(
                    new Pair<>(ent.getKey(), index + 1), //
                    ent.getValue().get(index)
                ))
                // Map.Entry<Pair<>, Value> - this is filter to exclude non requested channels from device
                .filter(item -> setPairDC.contains(item.getKey()))
                // Map<Name, Value>
                .collect(Collectors.toMap(
                    pair -> modBusChannels.getName(pair.getKey()),
                    Map.Entry::getValue
                ))
            )
            .flatMap(map -> map.entrySet().stream())
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue
            ));
    }
    /**
     * reads values from Device to set
     * @return
     */
    public Map<ChanName, ChanValue> values() {
        Map<Integer, WadAbstractDevice> devMap = devMap();
        Set<Pair<Integer, Integer>> setPairDC = setPairDC();

            // Map<Dev, Set<Channels>>
        return
        getMapDeviceChanList().keySet().stream() // Set<Dev>
            .collect(Collectors.toMap(
                key -> key,
                // this is real reading
                key -> devMap.get(key).channel(0).get().list()
                // this is fake reader
                //key -> IntStream.rangeClosed(1, devMap.getWoFail(key).properties().chanCount()).boxed().collect(Collectors.toList())
            ))
            // Map<Dev, List<Values>>
            .entrySet()
            // Set<Dev>
            .stream()
            .map(ent -> IntStream.range(0, ent.getValue().size())
                .mapToObj(index -> new AbstractMap.SimpleEntry<>(
                    new Pair<>(ent.getKey(), index + 1), //
                    ent.getValue().get(index)
                ))
                // Map.Entry<Pair<>, Value> - this is filter to exclude non requested channels from device
                .filter(item -> setPairDC.contains(item.getKey()))
                // Map<Name, Value>
                .collect(Collectors.toMap(
                    pair -> modBusChannels.getName(pair.getKey()),
                    Map.Entry::getValue
                ))
            )
            .flatMap(map -> map.entrySet().stream())
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue
            ));
    }
}
