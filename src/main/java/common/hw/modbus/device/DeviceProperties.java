package common.hw.modbus.device;

/**
 * Created by alexr on 15.01.2017.
 */
public class DeviceProperties {
    // Signal type: Analog / Digital
    private final SignalType signalType;
    // DRPort type: Input / Output / EnRelay
    private final PortType portType;
    // Channels count
    private final int channels;

    public DeviceProperties(SignalType signalType, PortType portType, int channels) {
        this.signalType = signalType;
        this.portType = portType;
        this.channels = channels;
    }

    public SignalType signalType() {
        return this.signalType;
    }

    public PortType portType() {
        return this.portType;
    }

    public int channels() {
        return this.channels;
    }

    @Override
    public String toString() {
        return
            "SignalType:"+this.signalType+" "+
            "PortType:"+this.portType+" "+
            "Channels:"+this.channels;
    }
}
