package app.persistence.init;

import java.util.EnumMap;
import java.util.HashMap;

/**
 * Created by mac on 24.07.2017.
 */
public interface EnumMapFrom<K extends Enum<K>,V> {
    EnumMap<K, V> enumMap() throws Exception;
}
