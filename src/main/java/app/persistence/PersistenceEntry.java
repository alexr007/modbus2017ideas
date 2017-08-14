package app.persistence;

import constants.ChanName;
import jwad.chanvalue.ChanValue;

public class PersistenceEntry {
    private final ChanName name;
    private ChanValue value;
    private final ValuesTail tail;

    public PersistenceEntry(ChanName name, int size) {
        this.name = name;
        this.value = ChanValue.None();
        this.tail = new ValuesTail(size);
    }

    void addNewValue(ChanValue value) {
        this.value = value;
        tail.add(value);
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
