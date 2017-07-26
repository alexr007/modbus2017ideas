package app.persistence;

import app.persistence.init.chan.ChannelList;
import app.persistence.init.chan.ModBusChannels;
import app.persistence.init.dev.ModBusDevices;
import constants.ChanName;
import constants.DevName;
import jbus.comport.COMPort;
import jbus.comport.COMPortFake;
import jbus.comport.COMPortProperties;
import jbus.comport.SimpleSerialInterface;
import jbus.modbus.ModBus;
import jwad.modules.WadAbstractDevice;
import jwad.channels.WAD_Channel;
import jssc.SerialPort;
import jssc.SerialPortException;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Triplet;
import java.util.ArrayList;
import java.util.Set;

import static constants.Ch.*;
import static constants.ChanName.*;
import static constants.DevName.*;

/**
 * Created by alexr on 27.04.2017.
 */
public class BIOcore {
    private final ModBusDevices devices;
    private final ModBusChannels channels;
    private final ModBus modBus;
    private final SimpleSerialInterface comPort;

    public BIOcore(String comName, boolean fake) throws Exception {
        this.comPort = new COMPortFake(
            comName,
            new COMPortProperties(SerialPort.BAUDRATE_57600)
        );
        this.modBus = new ModBus(
            this.comPort
        );
        // device list builded
        this.devices = new ModBusDevices(
            modBus,
            DeviceBuilded.buildEcoAlliance()
            //DeviceBuilded.buildTestEnvironment()
        );
        // channel list builded
        this.channels = new ModBusChannels(
            this.devices,
            // AIK1
            new Pair<>(AIK1,
                new ChannelList(
                    new Pair<>(S_A_CRUSH,  n1),
                    new Pair<>(S_А_GRAN,   n2),
                    new Pair<>(S_VOLT_IN,  n3)
                )),
            // AIK2
            new Pair<>(AIK2,
                new ChannelList(
                    new Pair<>(S_TG_TEMP,  n1),
                    new Pair<>(S_RAW_T_IN, n2),
                    new Pair<>(S_RAW_T_OUT, n3),
                    new Pair<>(S_TG_PRESS, n4)
                )),
            // AO1
            new Pair<>(AO1,
                new ChannelList(
                    new Pair<>(M_FU,  n1),
                    new Pair<>(M_AIR1, n2),
                    new Pair<>(M_AIR2, n3)
                )),
            // AO2
            new Pair<>(AO2,
                new ChannelList(
                    new Pair<>(M_AIR3,  n1),
                    new Pair<>(M_DRDR, n2),
                    new Pair<>(M_CYCL, n3),
                    new Pair<>(M_DISPEN, n4)
                )),
            new Pair<>(DI1,
                new ChannelList(
                    new Pair<>(S_CYL1_MIN, n1),
                    new Pair<>(S_CYL1_MAX, n2),
                    new Pair<>(S_CYL2_MIN, n3),
                    new Pair<>(S_CYL2_MAX, n4),
                    new Pair<>(SM_HYDRO, n5),
                    new Pair<>(SM_RAW, n6),
                    new Pair<>(SM_TRANS_1, n7),
                    new Pair<>(SM_SEP, n8),
                    new Pair<>(SM_TRANS_2, n9),
                    new Pair<>(SM_GATE_1, n10),
                    new Pair<>(STOP_1, n11),
                    new Pair<>(AUTO_MAN_1, n12),
                    new Pair<>(SM_FU_TR, n13),
                    new Pair<>(S_FU_BK_MIN, n14),
                    new Pair<>(S_FU_BK_MAX, n15)
                )),
            new Pair<>(DI2,
                new ChannelList(
                    new Pair<>(S_CRUSH_SEC, n1),
                    new Pair<>(SМ_FU_HAYM, n2),
                    new Pair<>(SM_FU, n3),
                    new Pair<>(SM_AIR1, n4),
                    new Pair<>(SM_AIR2, n5),
                    new Pair<>(SM_AIR3, n6),
                    new Pair<>(SM_DRDR, n7),
                    new Pair<>(SM_CYCL, n8),
                    new Pair<>(SM_GATE_2, n9),
                    new Pair<>(SM_CRUSH, n10),
                    new Pair<>(SM_FAN, n11),
                    new Pair<>(SM_GATE_3, n12),
                    new Pair<>(STOP_2, n13),
                    new Pair<>(AUTO_MAN_2, n14)
                    // 15 free
                )),
            new Pair<>(DI3,
                new ChannelList(
                    new Pair<>(STOP_3, n1),
                    new Pair<>(AUTO_MAN_3, n2),
                    new Pair<>(S_BUNK_MIN, n3),
                    new Pair<>(S_BUNK_MAX, n4),
                    new Pair<>(S_GRAN_SEC, n5),
                    new Pair<>(SM_HAYM, n6),
                    new Pair<>(SM_DISPEN, n7),
                    new Pair<>(SM_FORCE, n8),
                    new Pair<>(SM_GRAN, n9),
                    new Pair<>(SM_TRANS_3, n10),
                    new Pair<>(SM_GATE_4, n11),
                    new Pair<>(S_COOL_MIN, n12),
                    new Pair<>(S_COOL_MAX, n13),
                    new Pair<>(S_OUT_OP, n14),
                    new Pair<>(S_OUT_CL, n15)
                )),
            new Pair<>(DI4,
                new ChannelList(
                    new Pair<>(SM_EXHAU, n1),
                    new Pair<>(SM_OUT_OP, n2),
                    new Pair<>(SM_OUT_CL, n3),
                    new Pair<>(SM_SIEVE, n4),
                    new Pair<>(SM_TRANS_4, n5),
                    new Pair<>(SM_VIBRO_FILTR, n6)
                    // 7-15 free
                )),
            new Pair<>(DOS1,
                new ChannelList(
                    new Pair<>(VALVE_1, n1),
                    new Pair<>(VALVE_2, n2),
                    new Pair<>(VALVE_3, n3),
                    new Pair<>(VALVE_4, n4),
                    new Pair<>(VALVE_START, n5),
                    new Pair<>(M_HYDRO, n6),
                    new Pair<>(M_RAW, n7),
                    new Pair<>(M_TRANS_1, n8)
                )),
            new Pair<>(DOS2,
                new ChannelList(
                    new Pair<>(M_SEP, n1),
                    new Pair<>(M_TRANS_2, n2),
                    new Pair<>(M_GATE_1, n3),
                    new Pair<>(M_FU_TR, n4),
                    new Pair<>(M_FU_ON, n5),
                    new Pair<>(M_AIR1_ON, n6),
                    new Pair<>(M_AIR2_ON, n7),
                    new Pair<>(M_AIR3_ON, n8)
                )),
            new Pair<>(DOS3,
                new ChannelList(
                    new Pair<>(М_FU_HAYM, n1),
                    new Pair<>(M_DRDR_ON, n2),
                    new Pair<>(M_CYCL_ON, n3),
                    new Pair<>(M_GATE_2, n4),
                    new Pair<>(M_CRUSH, n5),
                    new Pair<>(M_FAN, n6),
                    new Pair<>(M_GATE_3, n7)
                    // 8 free
                )),
            new Pair<>(DOS4,
                new ChannelList(
                    new Pair<>(M_HAYM, n1),
                    new Pair<>(M_DISPEN_ON, n2),
                    new Pair<>(M_FORCE, n3),
                    new Pair<>(M_GRAN, n4),
                    new Pair<>(M_TRANS_3, n5),
                    new Pair<>(M_GATE_4, n6),
                    new Pair<>(M_EXHAUST, n7),
                    new Pair<>(M_OUT_OPEN, n8)
                )),
            new Pair<>(DOS5,
                new ChannelList(
                    new Pair<>(M_OUT_CLOSE, n1),
                    new Pair<>(М_SIEVE, n2),
                    new Pair<>(M_TRANS_4, n3),
                    new Pair<>(M_VIBRO_FILTR, n4)
                    // 5-8 free
                ))
            );
    }

    public WadAbstractDevice dev(DevName name) {
        try {
            return devices.get(name);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Module Name NotFound:%s",name.toString()));
        }
    }

    public WadAbstractDevice dev(String name) {
        try {
            return devices.get(name);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Module Name NotFound:%s",name));
        }
    }

    public WAD_Channel chan(ChanName name) throws Exception {
        try {
            return channels.get(name);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Channel Name NotFound:%s",name.toString()));
        }
    }

    public WAD_Channel chan(String name) throws Exception {
        try {
            return channels.get(name);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Channel Name NotFound:%s",name));
        }
    }

    public Set<DevName> devList() {
        return devices.list();
    }

    public ArrayList<Triplet> devListTriplet() {
        return devices.triplet();
    }

    public ArrayList<Quartet> chanListQuartet() {
        return channels.quartet();
    }

    public ModBus modBus() {
        return modBus;
    }

    public ModBusChannels channels() {
        return channels;
    }

    public void finish() throws SerialPortException {
        modBus().finish();
    }
}
