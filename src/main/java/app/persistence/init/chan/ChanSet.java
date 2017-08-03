package app.persistence.init.chan;

import constants.ChanName;
import jwad.chanvalue.ChanValue;
import jwad.modules.WadAbstractDevice;
import org.javatuples.Pair;

import java.util.*;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
    Map<ChanName, Integer> values() {
        Map<Integer, Set<Integer>> devChan = getMapDeviceChanList();
        Map<Integer, WadAbstractDevice> devMap = devMap();

        // <dev, list<values>>
        Map<Integer, List<Integer>> valuesMap = devChan.keySet().stream()
            .collect(Collectors.toMap(
                key -> key,
                key -> devMap.get(key).channel(0).get().list())
            );

        Map<Pair<Integer, Integer>, Integer> valuesMap2 = valuesMap.entrySet()
            .stream() // <dev, list<values>>
            .map(new Function<Map.Entry<Integer, List<Integer>>, Map<Pair<Integer, Integer>, Integer>>() {
                @Override
                public Map<Pair<Integer, Integer>, Integer> apply(Map.Entry<Integer, List<Integer>> ent) {
                    /** should map:
                     *     Map.Entry<dev, List<values>>
                     *     Map<Pair<dev, chan>, value>
                     */
                    return IntStream.range(0, ent.getValue().size())
                        .mapToObj(new IntFunction<Map.Entry<Pair<Integer, Integer>, Integer>>() {
                            @Override
                            public Map.Entry<Pair<Integer, Integer>, Integer> apply(int index) {
                                return
                                    new AbstractMap.SimpleEntry<>(
                                        new Pair<>(ent.getKey(), index+1),
                                        ent.getValue().get(index)
                                    );
                            }
                        })
                        .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue
                        ));
                }
            })
            .flatMap(map -> map.entrySet().stream())
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue
            ));

        Map<ChanName, Integer> collect = valuesMap2.entrySet()
            .stream()
            .map(entry -> new AbstractMap.SimpleEntry<>(modBusChannels.getName(entry.getKey()), entry.getValue()))
            .collect(Collectors.toMap(
                AbstractMap.SimpleEntry::getKey,
                AbstractMap.SimpleEntry::getValue
            ));

        return collect;
    }

    public Map<ChanName, Integer> values1() {
        Map<Integer, WadAbstractDevice> devMap = devMap();
        return
            getMapDeviceChanList().keySet().stream()
            .collect(Collectors.toMap(
                key -> key,
                //key -> devMap.get(key).channel(0).get().list())
                key -> IntStream.range(0, devMap.get(key).properties().chanCount()).boxed().collect(Collectors.toList())
            ))
            .entrySet()
            .stream()
            .map(ent -> IntStream.range(0, ent.getValue().size())
                    .mapToObj(index -> new AbstractMap.SimpleEntry<>(
                        // TODO тут есть лишний маппинг
                        // прочитанные порты, которые не сконфигурированы
                        // выдают ошибку
                        modBusChannels.getName(new Pair<>(ent.getKey(), index + 1)),
                        ent.getValue().get(index)
                    ))
                    .collect(Collectors.toMap(
                        Map.Entry::getKey,
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
