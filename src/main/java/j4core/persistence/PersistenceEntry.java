package j4core.persistence;

import j4core.tail.ValuesTail;
import constants.ChanName;
import j3wad.chanvalue.ChanValue;

public final class PersistenceEntry {
    private final ChanName name;
    private ChanValue value;
    private final ValuesTail tail;

    public PersistenceEntry(ChanName name, int size) {
        this.name = name;
        this.tail = new ValuesTail(size);
        this.value = ChanValue.None();
    }

    void addNewValue(ChanValue value) {
        this.value = value;
        tail.add(value);
    }

    public ChanName name() {
        return name;
    }

    public ChanValue value() {
        return value;
    }

    public ValuesTail tail() {
        return tail;
    }

    @Override
    public String toString() {
        return String.format("Tail size:%d, Tail:%s",tail.size(),tail.list().toString());
    }
}
