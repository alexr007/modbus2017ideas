package j4core;

import j4core.entity.chan.ModBusChannels;
import constants.ChanName;
import j1base.hex.HexFromByte;
import j3wad.WadDevType;
import j3wad.chanvalue.ChanValue;
import j3wad.modules.WadAbstractDevice;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static j3wad.WadDevType.*;

public final class ChannelsSet {
    private final ModBusChannels modBusChannels;
    private final Set<WadDevType> wadWritable = new HashSet<>(Arrays.asList(DOS, AO, AO6));

    public ChannelsSet(ModBusChannels modBusChannels) {
        this.modBusChannels = modBusChannels;
    }

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

    public void write(HashMapFrom<ChanName, ChanValue> origin) {
        write(origin.map());
    }

    public void write(Map<ChanName, ChanValue> origin) {
        //checkIdentically(origin);
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

    /**
     * class to represent Pair<device_id, channel_id>
     *     and chech
     */
    private class DCPairs {
        private final Set<Pair<Integer, Integer>> origin;

        DCPairs(Set<ChanName> chanSet) {
            this.origin = chanSet.stream()
                .map(item -> modBusChannels.getDC(item))
                .collect(Collectors.toSet());
        }

        boolean contains (Pair<Integer, Integer> pair) {
            return origin.contains(pair);
        }

        boolean contains (Triplet<Integer, Integer, ChanValue> trip) {
            return contains(trip.removeFrom2());
        }
    }

    public Map<ChanName, ChanValue> read(ChanName... chanList) {
        return read(Arrays.asList(chanList));
    }

    public Map<ChanName, ChanValue> read(List<ChanName> chanList) {
        return read(new HashSet<>(chanList));
    }

    public Map<ChanName, ChanValue> read(Set<ChanName> chanSet) {
        DCPairs pairs = new DCPairs(chanSet);
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
