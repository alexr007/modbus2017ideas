package tests.erasusre;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by alexr on 18.02.2017.
 */
public class Case2 {
    class E {
        void run() {
            switch (this.getClass().getSimpleName()) {
                case "Entity" : this.method0();
                    break;
                case "Entity1" : ((E1)this).method1();
                    break;
                case "Entity2" : ((E2)this).method2();
                    break;
            }
        }
        void method0() {
            System.out.println("Entity.m0");
        }
    }

    public class E1 extends E {
        void method1() {
            System.out.println("Entity1.m1");
        }
    }

    public class E2 extends E {
        void method2() {
            System.out.println("Entity2.m2");
        }
    }

    private final HashMap<String, E> map = new HashMap<>();
    private final ArrayList<E> list = new ArrayList<>();

    public void main() {
        map.put("0", new E());
        map.put("aa", new E1());
        map.put("2", new E2());

        map.get("0").method0();
        // variant aa
        E1 e1 = (E1)map.get("aa");
        e1.method1();
        // variant 2
        ((E1)map.get("aa")).method1();


        ((E2)map.get("2")).method2();

        list.add(new E());
        list.add(new E1());
        list.add(new E2());
        for (E item: list) {
            item.run();
        }
    }

}
