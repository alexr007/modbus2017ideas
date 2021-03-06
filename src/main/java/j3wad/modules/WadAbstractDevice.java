package j3wad.modules;

import com.google.common.base.Joiner;
import j1base.primitives.Bytes;
import j2bus.modbus.response.RqInfo;
import j2bus.modbus.response.RsAnalyzed;
import j2bus.modbus.response.RsParsed;
import jssc.SerialPortException;
import j1base.hex.HexFromByte;
import j2bus.modbus.*;
import j2bus.modbus.device.DeviceProperties;
import j3wad.WadDevType;
import j3wad.channels.WAD_Channel;
import j3wad.summary.WadSummary;
import org.xembly.Directives;

import java.io.IOException;

public abstract class WadAbstractDevice {
    private static final String ERROR_MESSAGE_MODBUS = "Something ModBus error occurred";
    private static final String ERROR_UNKNOWN_TYPE = "Unknown ModBus device type: %s";

    private final ModBusInterface modbus;
    private final int deviceId;
    private final WadDevType type;
    private final DeviceProperties properties;
    private final ModBusRequestBuilder builder;
    protected WadSummary summary;

    public WadAbstractDevice(ModBusInterface modbus, int deviceId, WadDevType type, DeviceProperties properties) {
        this.modbus = modbus;
        this.deviceId = deviceId;
        this.type = type;
        this.properties = properties;
        this.builder = new ModBusRequestBuilder(deviceId);
    }

    /**
     * @return device type AIK, DOS, AO etc
     */
    public WadDevType type() {
        return type;
    }

    /**
     * @return WAD_Channel instance
     */
    public abstract WAD_Channel channel(int chan);

    /**
     * @return device temperature in celsius
     */
    public int temperature() throws IOException {
        throw new IllegalArgumentException("Method temperature not implemented on this device type");
    }

    /**
     * @return ModBus device id: 1..255
     */
    public int id() {
        return deviceId;
    }

    /**
     * @return device name WAD_AIK_BUS, WAD_DOS_BUS etc
     */
    public CharSequence name() {
        return this.getClass().getSimpleName();
    }

    private ModBusRequestBuilder builder() {
        return builder;
    }

    public DeviceProperties properties() {
        return properties;
    }

    @Override
    public String toString() {
        return String.format("%-13s id: %s",
            name(),
            new HexFromByte(deviceId).toString()
        );
    }

    public CharSequence summaryTxt() {
        CharSequence details;
        CharSequence base = Joiner.on("\n").join(
            "Summary:",
            String.format("ModBus id: %s", new HexFromByte(deviceId)),
            String.format("Device name: %s", name()),
            String.format("Device type: %s", properties.portType()),
            String.format("Channels count: %s", properties.chanCount()),
            String.format("Channels type: %s", properties.signalType())
        );
        try {
            details = summary.txt();
//            details = summaryDetailsTxt();
        } catch (Exception e) {
            details = ERROR_MESSAGE_MODBUS+"\n";
        }
        return Joiner.on("\n").join(base, details);
    }

    public Directives summaryXml() {
        // основное
        Directives base = new Directives()
            .add("data")
            .add("summary")
            .add("modbusid").set(new HexFromByte(deviceId).toString()).up()
            .add("dname").set(name()).up()
            .add("dtype").set(properties.portType()).up()
            .add("ccount").set(properties.chanCount()).up()
            .add("ctype").set(properties.signalType()).up()
            .up();
        // детали
        Directives details;
        try {
            details = new Directives().add("channelMap").append(summary.xmlDir());
            //details = new Directives().add("channelMap").append(summaryDetailsXml());
        } catch (Exception e) {
            details = new Directives().add("error").set(ERROR_MESSAGE_MODBUS).up()
                .add("message").set(e.getMessage()).up();
        }
        return base.append(details);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof WadAbstractDevice
            && this.deviceId == WadAbstractDevice.class.cast(obj).deviceId
            && this.modbus.equals(WadAbstractDevice.class.cast(obj).modbus);
    }

    public <T extends WadAbstractDevice> T cast() {
        return (T)this;
    }

    public static WadAbstractDevice build(ModBus modBus, WadDevType type, int modbusId) {
        WadAbstractDevice device;
        switch (type) {
            case AIK: device = new WAD_AIK_BUS(modBus, modbusId);
                break;
            case AO: device = new WAD_AO_BUS(modBus, modbusId);
                break;
            case AO6: device = new WAD_AO6_BUS(modBus, modbusId);
                break;
            case DI: device = new WAD_DI_BUS(modBus, modbusId);
                break;
            case DI14: device = new WAD_DI14_BUS(modBus, modbusId);
                break;
            case DOS: device = new WAD_DOS_BUS(modBus, modbusId);
                break;
            default: throw new IllegalArgumentException(String.format(ERROR_UNKNOWN_TYPE, type));
        }
        return device;
    }

    public void write_(int baseReg, Bytes data)  {
        write_(baseReg, 1, data);
    }

    public void write_(int baseReg, int count, Bytes data)  {
        modbus.run( builder().cmdWriteRegister(baseReg, count, data ) );
    }

    public RsAnalyzed read_(int baseReg)  {
        return read_(baseReg, 1);
    }

    public RsAnalyzed read_(int baseReg, int regCount)  {
        return new RsAnalyzed(
            modbus.run(builder().cmdReadRegister(baseReg,regCount)),
            new RqInfo(id(), RsParsed.cmdRead, regCount*2)
        );
    }
}
