package common.hw.modbus.wad;

import com.google.common.base.Joiner;
import common.sw.common.IntAsHex;
import common.hw.modbus.*;
import common.hw.modbus.device.DeviceProperties;
import common.hw.modbus.response.InvalidModBusResponse;
import jssc.SerialPortException;
import org.xembly.Directives;
import org.xembly.ImpossibleModificationException;

public abstract class ModBusAbstractDevice {
    private final ModBus modbus;
    protected final int deviceId;
    protected final ModBusRequestBuilder builder;
    public DeviceProperties properties;

    public ModBusAbstractDevice(ModBus modbus, int deviceId) {
        this.modbus = modbus;
        this.deviceId = deviceId;
        this.builder = new ModBusRequestBuilder(deviceId);
    }

    public abstract WAD_Channel channel(int chan);
    public abstract int temperature() throws SerialPortException, InvalidModBusResponse, InvalidModBusFunction;

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

    public String summary() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        return Joiner.on("\n").join(
            "Summary:",
            String.format("ModBus id: %s", new IntAsHex(this.deviceId)),
            String.format("Device name: %s", this.getClass().getSimpleName()),
            String.format("Device type: %s", this.properties.portType()),
            String.format("Channels count: %s", this.properties.channels()),
            String.format("Channels type: %s", this.properties.signalType()),
            summaryDetailsTxt()
        );
    }

    public String summaryDetailsTxt() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        return "";
    }

    public Directives summaryDetailsXml() throws InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        return new Directives();
    }

    public Directives xml() throws ImpossibleModificationException, InvalidModBusResponse, SerialPortException, InvalidModBusFunction {
        return new Directives()
            .add("data")
            .add("summary")
            .add("modbusid").set(new IntAsHex(this.deviceId).toString()).up()
            .add("dname").set(this.getClass().getSimpleName()).up()
            .add("dtype").set(this.properties.portType()).up()
            .add("ccount").set(this.properties.channels()).up()
            .add("ctype").set(this.properties.signalType()).up()
            .up()
            .add("channels")
            .append(summaryDetailsXml());
    }

    public static ModBusAbstractDevice build(ModBus modBus, WadDevType type, int modbusId) {
        ModBusAbstractDevice device = null;
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
            default: break;
        }
        return device;
    }

}
