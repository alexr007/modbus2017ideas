package j2bus.modbus.response;

public interface MappedTo<T> {
    T map(int value);
}
