package app.persistence;

import app.persistence.init.ModBusChannels;
import app.persistence.init.ModBusDevices;
import constants.Ch;
import constants.Dv;
import jbus.comport.COMPort;
import jbus.comport.COMPortProperties;
import jbus.modbus.ModBus;
import jwad.modules.WadAbstractDevice;
import jwad.channels.WAD_Channel;
import jssc.SerialPort;
import jssc.SerialPortException;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by alexr on 27.04.2017.
 */
public class BIOcore {
    private final ModBusDevices devices;
    private final ModBusChannels channels;
    private final ModBus modBus;
    private final COMPort comPort;

    public BIOcore(String comName) throws Exception {
        this.comPort = new COMPort(
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
            new Pair<>(this.devices.get(Dv.AIK1),
                new ChannelList(
                    new Pair<>(Ch.S_A_CRUSH,  Ch.n1),
                    new Pair<>(Ch.S_А_GRAN,   Ch.n2),
                    new Pair<>(Ch.S_VOLT_IN,  Ch.n3)
                )),
            // AIK2
            new Pair<>(this.devices.get(Dv.AIK2),
                new ChannelList(
                    new Pair<>(Ch.S_TG_TEMP,  Ch.n1),
                    new Pair<>(Ch.S_RAW_T_IN, Ch.n2),
                    new Pair<>(Ch.S_RAW_T_OUT,Ch.n3),
                    new Pair<>(Ch.S_TG_PRESS, Ch.n4)
                )),
            // AO1
            new Pair<>(this.devices.get(Dv.AO1),
                new ChannelList(
                    new Pair<>(Ch.M_FU,  Ch.n1),
                    new Pair<>(Ch.M_AIR1, Ch.n2),
                    new Pair<>(Ch.M_AIR2,Ch.n3)
                )),
            // AO2
            new Pair<>(this.devices.get(Dv.AO2),
                new ChannelList(
                    new Pair<>(Ch.M_AIR3,  Ch.n1),
                    new Pair<>(Ch.M_DRDR, Ch.n2),
                    new Pair<>(Ch.M_CYCL,Ch.n3),
                    new Pair<>(Ch.M_DISPEN, Ch.n4)
                )),
            new Pair<>(this.devices.get(Dv.DI1),
                new ChannelList(
                    new Pair<>(Ch.S_CYL1_MIN, Ch.n1),
                    new Pair<>(Ch.S_CYL1_MAX, Ch.n2),
                    new Pair<>(Ch.S_CYL2_MIN, Ch.n3),
                    new Pair<>(Ch.S_CYL2_MAX, Ch.n4),
                    new Pair<>(Ch.SM_HYDRO, Ch.n5),
                    new Pair<>(Ch.SM_RAW, Ch.n6),
                    new Pair<>(Ch.SM_TRANS_1, Ch.n7),
                    new Pair<>(Ch.SM_SEP, Ch.n8),
                    new Pair<>(Ch.SM_TRANS_2, Ch.n9),
                    new Pair<>(Ch.SM_GATE_1, Ch.n10),
                    new Pair<>(Ch.STOP_1, Ch.n11),
                    new Pair<>(Ch.AUTO_MAN_1, Ch.n12),
                    new Pair<>(Ch.SM_FU_TR, Ch.n13),
                    new Pair<>(Ch.S_FU_BK_MIN, Ch.n14),
                    new Pair<>(Ch.S_FU_BK_MAX, Ch.n15)
                )),
            new Pair<>(this.devices.get(Dv.DI2),
                new ChannelList(
                    new Pair<>(Ch.S_CRUSH_SEC, Ch.n1),
                    new Pair<>(Ch.SМ_FU_HAYM, Ch.n2),
                    new Pair<>(Ch.SM_FU, Ch.n3),
                    new Pair<>(Ch.SM_AIR1, Ch.n4),
                    new Pair<>(Ch.SM_AIR2, Ch.n5),
                    new Pair<>(Ch.SM_AIR3, Ch.n6),
                    new Pair<>(Ch.SM_DRDR, Ch.n7),
                    new Pair<>(Ch.SM_CYCL, Ch.n8),
                    new Pair<>(Ch.SM_GATE_2, Ch.n9),
                    new Pair<>(Ch.SM_CRUSH, Ch.n10),
                    new Pair<>(Ch.SM_FAN, Ch.n11),
                    new Pair<>(Ch.SM_GATE_3, Ch.n12),
                    new Pair<>(Ch.STOP_2, Ch.n13),
                    new Pair<>(Ch.AUTO_MAN_2, Ch.n14)
                    // 15 free
                )),
            new Pair<>(this.devices.get(Dv.DI3),
                new ChannelList(
                    new Pair<>(Ch.STOP_3, Ch.n1),
                    new Pair<>(Ch.AUTO_MAN_3, Ch.n2),
                    new Pair<>(Ch.S_BUNK_MIN, Ch.n3),
                    new Pair<>(Ch.S_BUNK_MAX, Ch.n4),
                    new Pair<>(Ch.S_GRAN_SEC, Ch.n5),
                    new Pair<>(Ch.SM_HAYM, Ch.n6),
                    new Pair<>(Ch.SM_DISPEN, Ch.n7),
                    new Pair<>(Ch.SM_FORCE, Ch.n8),
                    new Pair<>(Ch.SM_GRAN, Ch.n9),
                    new Pair<>(Ch.SM_TRANS_3, Ch.n10),
                    new Pair<>(Ch.SM_GATE_4, Ch.n11),
                    new Pair<>(Ch.S_COOL_MIN, Ch.n12),
                    new Pair<>(Ch.S_COOL_MAX, Ch.n13),
                    new Pair<>(Ch.S_OUT_OP, Ch.n14),
                    new Pair<>(Ch.S_OUT_CL, Ch.n15)
                )),
            new Pair<>(this.devices.get(Dv.DI4),
                new ChannelList(
                    new Pair<>(Ch.SM_EXHAU, Ch.n1),
                    new Pair<>(Ch.SM_OUT_OP, Ch.n2),
                    new Pair<>(Ch.SM_OUT_CL, Ch.n3),
                    new Pair<>(Ch.SM_SIEVE, Ch.n4),
                    new Pair<>(Ch.SM_TRANS_4, Ch.n5),
                    new Pair<>(Ch.SM_VIBRO_FILTR, Ch.n6)
                    // 7-15 free
                )),
            new Pair<>(this.devices.get(Dv.DOS1),
                new ChannelList(
                    new Pair<>(Ch.VALVE_1, Ch.n1),
                    new Pair<>(Ch.VALVE_2, Ch.n2),
                    new Pair<>(Ch.VALVE_3, Ch.n3),
                    new Pair<>(Ch.VALVE_4, Ch.n4),
                    new Pair<>(Ch.VALVE_START, Ch.n5),
                    new Pair<>(Ch.M_HYDRO, Ch.n6),
                    new Pair<>(Ch.M_RAW, Ch.n7),
                    new Pair<>(Ch.M_TRANS_1, Ch.n8)
                )),
            new Pair<>(this.devices.get(Dv.DOS2),
                new ChannelList(
                    new Pair<>(Ch.M_SEP, Ch.n1),
                    new Pair<>(Ch.M_TRANS_2, Ch.n2),
                    new Pair<>(Ch.M_GATE_1, Ch.n3),
                    new Pair<>(Ch.M_FU_TR, Ch.n4),
                    new Pair<>(Ch.M_FU_ON, Ch.n5),
                    new Pair<>(Ch.M_AIR1_ON, Ch.n6),
                    new Pair<>(Ch.M_AIR2_ON, Ch.n7),
                    new Pair<>(Ch.M_AIR3_ON, Ch.n8)
                )),
            new Pair<>(this.devices.get(Dv.DOS3),
                new ChannelList(
                    new Pair<>(Ch.М_FU_HAYM, Ch.n1),
                    new Pair<>(Ch.M_DRDR_ON, Ch.n2),
                    new Pair<>(Ch.M_CYCL_ON, Ch.n3),
                    new Pair<>(Ch.M_GATE_2, Ch.n4),
                    new Pair<>(Ch.M_CRUSH, Ch.n5),
                    new Pair<>(Ch.M_FAN, Ch.n6),
                    new Pair<>(Ch.M_GATE_3, Ch.n7)
                    // 8 free
                )),
            new Pair<>(this.devices.get(Dv.DOS4),
                new ChannelList(
                    new Pair<>(Ch.M_HAYM, Ch.n1),
                    new Pair<>(Ch.M_DISPEN_ON, Ch.n2),
                    new Pair<>(Ch.M_FORCE, Ch.n3),
                    new Pair<>(Ch.M_GRAN, Ch.n4),
                    new Pair<>(Ch.M_TRANS_3, Ch.n5),
                    new Pair<>(Ch.M_GATE_4, Ch.n6),
                    new Pair<>(Ch.M_EXHAUST, Ch.n7),
                    new Pair<>(Ch.M_OUT_OPEN, Ch.n8)
                )),
            new Pair<>(this.devices.get(Dv.DOS5),
                new ChannelList(
                    new Pair<>(Ch.M_OUT_CLOSE, Ch.n1),
                    new Pair<>(Ch.М_SIEVE, Ch.n2),
                    new Pair<>(Ch.M_TRANS_4, Ch.n3),
                    new Pair<>(Ch.M_VIBRO_FILTR, Ch.n4)
                    // 5-8 free
                ))
            );
        System.out.println(
            this.channels.toString()
        );
    }

    public WadAbstractDevice dev(CharSequence name) {
        try {
            return devices.get(name);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Module Name NotFound:%s",name));
        }
    }

    public WAD_Channel chan(CharSequence name) throws Exception {
        try {
            return channels.get(name);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Channel Name NotFound:%s",name));
        }
    }

    public Set<CharSequence> devList() {
        return devices.list();
    }

    public ArrayList<Triplet> devListTriplet() {
        return devices.triplet();
    }

    public ArrayList<Triplet> chanListTriplet() {
        return channels.triplet();
    }

    public ModBus modBus() {
        return modBus;
    }

    public void finish() throws SerialPortException {
        modBus().finish();
    }
}
