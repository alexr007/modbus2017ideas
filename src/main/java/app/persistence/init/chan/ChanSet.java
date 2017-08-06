package app.persistence.init.chan;

import constants.ChanName;
import jbase.hex.HexFromByte;
import jbus.modbus.InvalidModBusFunction;
import jssc.SerialPortException;
import jwad.WadDevType;
import jwad.channels.WAD_Channel;
import jwad.chanvalue.ChanValue;
import jwad.chanvalue.IntFromChanValue;
import jwad.chanvalue.TypeDO;
import jwad.modules.WadAbstractDevice;
import org.javatuples.Pair;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

    private WAD_Channel chanNameToChannel(ChanName key) {
        return modBusChannels.channelMap().get(key);
    }

    /**
     * converts: Set<ChanName> to Set<Dev, Set<Channels>>
     * @return Map<Integer, Set<Integer>> mean Map<Dev, Set<Channels>>
     */
    public Map<Integer, Set<Integer>> getMapDeviceChanList () {
        return chanSet.stream()
            .map(this::chanNameToChannel) // map chanId -> channel
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
    private boolean checkIsDevWritable2(WadAbstractDevice dev) {
        if (!wadWritable.contains(dev.type())) {
            throw new IllegalArgumentException(String.format("Non writable device selected to write: %s, modbus id:%s",
                dev.type(), new HexFromByte(dev.id())));
        }
        else {
            return true;
        }
    }

    private WadAbstractDevice chanNameToDevId(ChanName chanName) {
        return modBusChannels.channelMap().get(chanName).device();
    }

    private WadAbstractDevice mapEntryToDevId(Map.Entry<ChanName, ChanValue> ent) {
        return modBusChannels.channelMap().get(ent.getKey()).device();
    }

    /**
     * Writes EnumMap<ChanName, ChanValue> to device
     * @param mapToWrite
     */
    public void write(Map<ChanName, ChanValue> mapToWrite) {
        checkIdentically(mapToWrite);
        // devices to Write: Map<id, WadDevice>
        Map<Integer, WadAbstractDevice> devsToWrite = mapToWrite.keySet().stream()
            .map(this::chanNameToDevId)
            .distinct()
            // check devices able to write or throw the exception
            .filter(this::checkIsDevWritable2)
            .collect(Collectors.toMap(
                dev -> dev.id(),
                dev -> dev
            ));
        // now need convert Map<ChanName, ChanValue> to:
        // Map<DevId, Map<ChanId, ChanValue>> for future traverse it.
        Map<Integer, Map<Integer, ChanValue>> collect = mapToWrite.entrySet().stream()
            .collect(
                Collectors.groupingBy(ent1-> modBusChannels.channelMap().get(ent1.getKey()).device().id(),
                    Collectors.toMap(
                        ent -> modBusChannels.channelMap().get(ent.getKey()).chanNumber(),
                        ent -> ent.getValue()
                    ))
            );

        System.out.println("Data to write Map<Dev, Map<Chan, Val>>");
        System.out.println(collect);
        // now writing
        devsToWrite.entrySet().stream()
            .forEach(new Consumer<Map.Entry<Integer, WadAbstractDevice>>() {
                @Override
                public void accept(Map.Entry<Integer, WadAbstractDevice> ent) {
                    // ModBus Device ID
                    int devId = ent.getKey();
                    // ModBus Device
                    WadAbstractDevice dev = ent.getValue();
                    Map<Integer, ChanValue> deviceEntry = collect.get(devId);

                    int mapSize = deviceEntry.size();
                    if (mapSize==1) {
                        // one channel write
                        System.out.println("writting 1 channel");
                        Map.Entry<Integer, ChanValue> entry = deviceEntry.entrySet().stream().findFirst().get();
                        dev.channel(entry.getKey()).set(entry.getValue());
                        // one channel write - done
                    } else if (mapSize==dev.properties().chanCount()) {
                        // write all channels
                        System.out.println(String.format("writting ALL %s channels", dev.properties().chanCount()));
                    } else {
                        // M from N channel write
                        System.out.println(String.format("writting %s of %s channels", mapSize, dev.properties().chanCount()));
                        // read all channels
                        // replace new channels
                        // write all channels
                    }
                }
            });
    }

    public Map<ChanName, Integer> values_v0_work_ok() {
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
