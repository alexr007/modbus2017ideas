package app.persistence.init;

import java.util.Map;

/**
 * Created by mac on 24.07.2017.
 */
public interface MapFrom<K, V> {
    Map<K, V> map() throws Exception;
}
