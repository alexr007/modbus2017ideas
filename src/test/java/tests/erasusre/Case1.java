package tests.erasusre;

import java.util.HashMap;

/**
 * Created by alexr on 18.02.2017.
 */
public class Case1 {
    class Entity {}

    public class Entity1 extends Entity {
        void method1() {
            System.out.println("Entity1.m1");
        }
    }

    public class Entity2 extends Entity {
        void method2() {
            System.out.println("Entity2.m2");
        }
    }

    private final HashMap<String, Entity> map = new HashMap<>();

    public void main() {
        map.put("aa", new Entity1());
        map.put("2", new Entity2());

        ((Entity1)map.get("aa")).method1();
        // variant aa
        Entity1 e1 = (Entity1)map.get("aa");
        e1.method1();
        // variant 2
        ((Entity1)map.get("aa")).method1();
    }

}
