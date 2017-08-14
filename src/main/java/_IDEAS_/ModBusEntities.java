package _IDEAS_;

import j4core.entity.chan.ModBusChannels;
import _IDEAS_.entities.EntityAbstract;

import java.util.HashMap;

/**
 * Created by alexr on 17.02.2017.
 */
public class ModBusEntities {
    private final ModBusChannels channels;
    private HashMap<String, EntityAbstract> entities = new HashMap<>();

    public ModBusEntities(ModBusChannels channels) {
        this.channels = channels;
    }

    public void add(String entityName, EntityAbstract entity) throws Exception {
        if (entities.containsKey(entityName)) {
            throw new Exception(String.format("Duplicate Entity Name:%s",entityName));
        }
        entities.put(entityName, entity);
    }

    public EntityAbstract get(String entityName) throws Exception {
        if (!entities.containsKey(entityName)) {
            throw new Exception(String.format("Entity Name NotFound:%s",entityName));
        }
        return entities.get(entityName);
    }

}
