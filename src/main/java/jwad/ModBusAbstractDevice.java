package jwad;

import com.google.common.base.Joiner;
import common.sw.common.IntAsHex;
import jbus.modbus.*;
import jbus.modbus.device.DeviceProperties;
import jbus.modbus.response.InvalidModBusResponse;
import jssc.SerialPortException;
import jwad.channels.WAD_Channel;
import jwad.modules.*;
import org.xembly.Directives;

public abstract class ModBusAbstractDevice {
    private final String ERROR_MESSAGE_MODBUS = "Something ModBus error occurred";
    private final ModBus modbus;
    private final int deviceId;
    public final ModBusRequestBuilder builder;
    public DeviceProperties properties;

    public ModBusAbstractDevice(ModBus modbus, int deviceId) {
        this.modbus = modbus;
        this.deviceId = deviceId;
        this.builder = new ModBusRequestBuilder(deviceId);
    }

    public abstract WadDevType type();
    public abstract WAD_Channel channel(int chan);
    public abstract int temperature() throws SerialPortException, InvalidModBusResponse, InvalidModBusFunction;

    public int id() {
        return deviceId;
    }

    public MbResponse run(MbRequest req) throws SerialPortException {
        return modbus.run(req);
    }

    @Override
    public String toString() {
        return
            String.format("%-13s id: %s",
                this.getClass().getSimpleName(),
                new IntAsHex(deviceId).toString()
            );
    }

    public String summary() {
        String ret;
        String s = Joiner.on("\n").join(
            "Summary:",
            String.format("ModBus id: %s", new IntAsHex(this.deviceId)),
            String.format("Device name: %s", this.getClass().getSimpleName()),
            String.format("Device type: %s", this.properties.portType()),
            String.format("Channels count: %s", this.properties.channels()),
            String.format("Channels type: %s", this.properties.signalType())
            );

        try {
            ret = summaryDetailsTxt();
        } catch (Exception e) {
            ret = ERROR_MESSAGE_MODBUS+"\n";
            e.printStackTrace();
        }
        return Joiner.on("\n").join(s, ret);
    }

    public String summaryDetailsTxt() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        return "";
    }

    public Directives summaryDetailsXml() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        return new Directives();
    }

    public Directives xml() {
        // основное
        Directives dirs = new Directives()
            .add("data")
            .add("summary")
            .add("modbusid").set(new IntAsHex(this.deviceId).toString()).up()
            .add("dname").set(this.getClass().getSimpleName()).up()
            .add("dtype").set(this.properties.portType()).up()
            .add("ccount").set(this.properties.channels()).up()
            .add("ctype").set(this.properties.signalType()).up()
            .up();
        // детали
        Directives details;
        try {
            details = new Directives().add("channels").append(summaryDetailsXml());
        } catch (Exception e) {
            details = new Directives().add("error").set(ERROR_MESSAGE_MODBUS).up()
            .add("message").set(e.getMessage()).up();
            //e.printStackTrace();
        }
        return dirs.append(details);
    }

    public static ModBusAbstractDevice build(ModBus modBus, WadDevType type, int modbusId) throws Exception {
        ModBusAbstractDevice device;
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
            default: throw new Exception(String.format("Unknown ModBus Type: %s", type));
        }
        return device;
    }
}
