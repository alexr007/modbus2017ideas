package j4core.tail;

import j3wad.chanvalue.ChanValue;
import j3wad.chanvalue.TypeChan;

import java.time.Instant;

/**
 * class to represent value (ChanValue)
 * at the concrete time moment (Instant)
 */
public final class ValuesTailEntry {
    private final ChanValue value;
    private final Instant instant = Instant.now();

    /**
     * Ctor
     * @param value
     */
    public ValuesTailEntry(ChanValue value) {
        this.value = value;
    }

    /**
     * get the value
     * @return ChanValue
     */
    public ChanValue value() {
        return value;
    }

    /**
     * get the instant when value readed
     * @return Instant
     */
    public Instant instant() {
        return instant;
    }

    /**
     * get the millis when value readed
     * @return Long
     */
    public Long millis() {
        return instant.toEpochMilli();
    }

    /**
     * get the channel type DI / DO / A
     * @return TypeChan
     */
    public TypeChan type() {
        return value.type();
    }

    @Override
    public String toString() {
        return String.format("Time:%s, Value:%s", millis(), value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ValuesTailEntry
            && this.value.equals(ValuesTailEntry.class.cast(obj).value)
            && this.instant.equals(ValuesTailEntry.class.cast(obj).instant);
    }
}
