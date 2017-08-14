package j2bus.modbus.device;

/**
 * Created by alexr on 15.01.2017.
 */
public class DeviceProperties {
    // Signal type: Analog / Digital
    private final SignalType signalType;
    // DRPort type: Input / Output / EnRelay
    private final PortType portType;
    // Channels count: 1-24
    private final int channels;

    public DeviceProperties(SignalType signalType, PortType portType, int count) {
        this.signalType = signalType;
        this.portType = portType;
        this.channels = count;
    }

    public SignalType signalType() {
        return this.signalType;
    }

    public PortType portType() {
        return this.portType;
    }

    public int chanCount() {
        return this.channels;
    }

    @Override
    public String toString() {
        return
            String.format("SignalType:%-7s, PortType:%-6s, Channels:%s",
                this.signalType,
                this.portType,
                this.channels);
    }
}
