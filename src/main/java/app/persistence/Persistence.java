package app.persistence;

import constants.ChanName;
import jwad.chanvalue.ChanValue;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Persistence implements PersistenceRead, PersistenceWrite {
    private final Map<ChanName, PersistenceEntry> map = new EnumMap<>(ChanName.class);
    private final static String ERR_NOTFOUND = "Entry not found:%s\n";

    public Persistence(Map<ChanName, Integer> config) {
        config.forEach((k,v)->map.put(k,new PersistenceEntry(k,v)));
    }

    @Override
    public PersistenceEntry read(ChanName name) {
        if (map.containsKey(name)) {
            return map.get(name);
        } else {
            throw new IllegalArgumentException(String.format(ERR_NOTFOUND, name));
        }
    }

    @Override
    public void write(ChanName name, ChanValue value) {
        if (map.containsKey(name)) {
            map.get(name).addNewValue(value);
        } else {
            throw new IllegalArgumentException(String.format(ERR_NOTFOUND, name));
        }
    }

    @Override
    public String toString() {
        return map.entrySet().stream()
            .map(entry -> String.format("Chan.name:%-13s, tail.size:%s",entry.getKey(),entry.getValue().tail().size()))
            .collect(Collectors.joining("\n"));
    }
}
