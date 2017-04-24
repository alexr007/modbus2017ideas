package common.sw.decision.test;

import common.sw.decision.Strings;

/**
 * Created by alexr on 24.04.2017.
 */
public class StringsTest {
    public void test2() {
        String[] strings1 = new String[]{"a", "b"};
        Strings strings2 = new Strings("a", "b");
        CharSequence[] strings3 = new Strings("a", "b").get();
        CharSequence[] charSequences = strings2.get();
    }
}
