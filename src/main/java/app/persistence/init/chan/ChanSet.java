package app.persistence.init.chan;

import constants.ChanName;
import jbase.hex.HexFromByte;
import jwad.WadDevType;
import jwad.chanvalue.ChanValue;
import jwad.modules.WadAbstractDevice;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.*;
import java.util.stream.Collectors;

import static jwad.WadDevType.*;

public class ChanSet {
    private final ModBusChannels modBusChannels;
    private final Set<ChanName> chanSet;
    private final Set<WadDevType> wadWritable = new HashSet<>(Arrays.asList(DOS, AO, AO6));


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
            .map(ent->modBusChannels.channelMap().get(ent)) // map chanId -> channel
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

    /**
     * check origin Set equals mapToWrite
     */
    private void checkIdentically(Map<ChanName, ChanValue> mapToWrite) {
        Set<ChanName> toWrite = new HashSet<>(mapToWrite.keySet());
        if (!chanSet.isEmpty() && !chanSet.equals(toWrite)) {
            throw new IllegalArgumentException(String.format(
                "Origin set and set to write is different\nOrigin: %s\nToWrite: %s\n",
                chanSet, toWrite));
        }
    }

    /**
     * check if selected device available to write
     */
    private void isDeviceWritable(WadAbstractDevice dev) {
        if (!wadWritable.contains(dev.type())) {
            throw new IllegalArgumentException(
                String.format("Non writable device selected to write: %s, modbus id:%s",
                dev.type(), new HexFromByte(dev.id()))
            );
        }
    }

    private Integer entryToChannelId(Map.Entry<ChanName, ChanValue> ent) {
        return modBusChannels.channelMap().get(ent.getKey()).chanNumber();
    }

    private WadAbstractDevice groupFunction(Map.Entry<ChanName, ChanValue> entry) {
        return modBusChannels.channelMap().get(entry.getKey()).device();
    }

    private void writeSingleDevice(WadAbstractDevice d, Map<Integer, ChanValue> valuesMap) {
        isDeviceWritable(d);
        if (valuesMap.size() == 1) {
            // write SINGLE channel
            Map.Entry<Integer, ChanValue> ent1 = valuesMap.entrySet().stream().findFirst().get();
            d.channel(ent1.getKey()).set(ent1.getValue());
        } else if (valuesMap.size() == d.properties().chanCount()) {
            // write ALL channels
            d.channel(0).set(valuesMap);
        } else {
            // write M of N channels ( read / replace / check / write )
            Map<Integer, ChanValue> readed = d.channel(0).get().map();
            Map<Integer, ChanValue> toWrite = new HashMap<>(readed);
            toWrite.replaceAll(valuesMap::getOrDefault);
            if (!readed.equals(toWrite))
                d.channel(0).set(toWrite);
        }
    }

    /**
     * Writes EnumMap<ChanName, ChanValue> to device
     * @param origin
     */
    public void write(Map<ChanName, ChanValue> origin) {
        checkIdentically(origin);
        origin.entrySet().stream()
            .collect(
                Collectors.groupingBy(this::groupFunction, // WadDevice
                    Collectors.toMap(
                        this::entryToChannelId, // channel id
                        Map.Entry::getValue // ChanValue
                    ))
            )
            .forEach(this::writeSingleDevice);
    }

    private class DCPairs {
        private final Set<Pair<Integer, Integer>> origin;

        DCPairs() {
            this.origin = chanSet.stream()
                .map(item -> modBusChannels.getDC(item))
                .collect(Collectors.toSet());
        }

        boolean contains (Pair<Integer, Integer> pair) {
            return origin.contains(pair);
        }

        boolean contains (Triplet<Integer, Integer, ChanValue> trip) {
            return contains(new Pair<>(trip.getValue0(), trip.getValue1()));
        }
    }

    public Map<ChanName, ChanValue> values() {
        DCPairs pairs = new DCPairs();
        return chanSet.stream()
            .map(ent -> modBusChannels.channelMap().get(ent).device())
            .distinct()
            .map(d -> d.channel(0).get().stream() // TODO написать правильный FAKE READER соответственно каждому типу устройства
                .map(cv -> new Triplet<>(d.id(), cv.getKey(), cv.getValue()))
                .collect(Collectors.toSet()))
            .flatMap(Collection::stream)
            .filter(pairs::contains)
            .collect(Collectors.toMap(
                modBusChannels::getName,
                Triplet::getValue2
            ));
    }
}
