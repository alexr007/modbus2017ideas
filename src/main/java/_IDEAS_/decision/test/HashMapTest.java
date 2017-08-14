package _IDEAS_.decision.test;

import java.util.HashMap;

/**
 * Created by alexr on 24.04.2017.
 */
public class HashMapTest {
    public void test() {
        HashMap<String, Integer> hm = new HashMap<>();
        hm.put("a",1);
        hm.put("b",2);
        System.out.println(hm);
        System.out.println(hm.keySet());
    }
}
