package jwad.modules;

import com.google.common.base.Joiner;
import common.sw.common.IntAsHex;
import jbus.modbus.*;
import jbus.modbus.device.DeviceProperties;
import jbus.modbus.response.InvalidModBusResponse;
import jssc.SerialPortException;
import jwad.WadDevType;
import jwad.channels.WAD_Channel;
import org.xembly.Directives;

public abstract class WadAbstractDevice {
    private static final String ERROR_MESSAGE_MODBUS = "Something ModBus error occurred";
    private static final String ERROR_UNKNOWN_TYPE = "Unknown ModBus Type: %s";

    private final ModBus modbus;
    private final int deviceId;
    private final DeviceProperties properties;
    private final ModBusRequestBuilder builder;

    public WadAbstractDevice(ModBus modbus, int deviceId, DeviceProperties properties) {
        this.modbus = modbus;
        this.deviceId = deviceId;
        this.properties = properties;
        this.builder = new ModBusRequestBuilder(deviceId);
    }

    /**
     * @return device type AIK, DOS, AO etc
     */
    public abstract WadDevType type();

    /**
     * @return WAD_Channel instance
     */
    public abstract WAD_Channel channel(int chan);

    /**
     * @return device temperature in celsius
     */
    public abstract int temperature() throws SerialPortException, InvalidModBusResponse, InvalidModBusFunction;

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

    public ModBusRequestBuilder builder() {
        return builder;
    }

    public DeviceProperties properties() {
        return properties;
    }

    public MbResponse run(MbRequest req) throws SerialPortException {
        return modbus.run(req);
    }

    @Override
    public String toString() {
        return
            String.format("%-13s id: %s",
                name(),
                new IntAsHex(deviceId).toString()
            );
    }

    public CharSequence summaryTxt() {
        CharSequence details;
        CharSequence base = Joiner.on("\n").join(
            "Summary:",
            String.format("ModBus id: %s", new IntAsHex(deviceId)),
            String.format("Device name: %s", name()),
            String.format("Device type: %s", properties.portType()),
            String.format("Channels count: %s", properties.chanCount()),
            String.format("Channels type: %s", properties.signalType())
        );
        try {
            details = summaryDetailsTxt();
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
            .add("modbusid").set(new IntAsHex(deviceId).toString()).up()
            .add("dname").set(name()).up()
            .add("dtype").set(properties.portType()).up()
            .add("ccount").set(properties.chanCount()).up()
            .add("ctype").set(properties.signalType()).up()
            .up();
        // детали
        Directives details;
        try {
            details = new Directives().add("channels").append(summaryDetailsXml());
        } catch (Exception e) {
            details = new Directives().add("error").set(ERROR_MESSAGE_MODBUS).up()
                .add("message").set(e.getMessage()).up();
        }
        return base.append(details);
    }

    public abstract CharSequence summaryDetailsTxt() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction;
    public abstract Directives summaryDetailsXml() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction;

    public static WadAbstractDevice build(ModBus modBus, WadDevType type, int modbusId) throws Exception {
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
            default: throw new Exception(String.format(ERROR_UNKNOWN_TYPE, type));
        }
        return device;
    }
}
