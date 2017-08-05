package entities;

/**
 * Created by alexr on 17.02.2017.
 */
public class AbstractEntity {
    protected final EntityType type;

    public AbstractEntity(EntityType type) {
        this.type = type;
    }

    public EntityType type() {
        return type;
    }
/*

    public boolean fails() throws InvalidEntityFunction, InvalidModBusResponse, ModBusInvalidFunction, SerialPortException {
        throw new InvalidEntityFunction();
    };
    public boolean opened() throws InvalidEntityFunction, InvalidModBusResponse, SerialPortException, ModBusInvalidFunction {
        throw new InvalidEntityFunction();
    };
    public boolean closed() throws InvalidEntityFunction, InvalidModBusResponse, SerialPortException, ModBusInvalidFunction {
        throw new InvalidEntityFunction();
    };
    public Values bytes() throws InvalidEntityFunction, InvalidModBusResponse, SerialPortException, ModBusInvalidFunction {
        throw new InvalidEntityFunction();
    };
    public void on() throws InvalidEntityFunction, Exception {
        throw new InvalidEntityFunction();
    };
    public void off() throws InvalidEntityFunction, Exception {
        throw new InvalidEntityFunction();
    };
    public void run(int val) throws InvalidEntityFunction, Exception {
        throw new InvalidEntityFunction();
    };
    public void open() throws Exception {
        throw new InvalidEntityFunction();
    };
    public void close() throws Exception {
        throw new InvalidEntityFunction();
    };
    public void stop() throws Exception {
        throw new InvalidEntityFunction();
    };
*/
}
