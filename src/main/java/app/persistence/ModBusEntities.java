package app.persistence;

import app.persistence.init.ModBusChannels;
import entities.AbstractEntity;

import java.util.HashMap;

/**
 * Created by alexr on 17.02.2017.
 */
public class ModBusEntities {
    private final ModBusChannels channels;
    private HashMap<String, AbstractEntity> entities = new HashMap<>();

    public ModBusEntities(ModBusChannels channels) {
        this.channels = channels;
    }

    public void add(String entityName, AbstractEntity entity) throws Exception {
        if (entities.containsKey(entityName)) {
            throw new Exception(String.format("Duplicate Entity Name:%s",entityName));
        }
        entities.put(entityName, entity);
    }

    public AbstractEntity get(String entityName) throws Exception {
        if (!entities.containsKey(entityName)) {
            throw new Exception(String.format("Entity Name NotFound:%s",entityName));
        }
        return entities.get(entityName);
    }

}
