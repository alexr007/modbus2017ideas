package jbus.modbus.response;

public interface MappedTo<T> {
    T map(int value);
}
