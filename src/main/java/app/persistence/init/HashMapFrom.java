package app.persistence.init;

import java.util.HashMap;

/**
 * Created by mac on 24.07.2017.
 */
public interface HashMapFrom<K,V> {
    HashMap<K, V> hashMap() throws Exception;
}
