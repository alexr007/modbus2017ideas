package tests.iterables;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;

import java.util.Arrays;

/**
 * Created by alexr on 19.02.2017.
 */
public class Test_Iterables {
    public static void main(String[] args) {
        final Iterable<String> words = Arrays.asList("abc","cde","fxp","qwer","asdf","zxcv");
        final Iterable<String> transformed = Iterables.transform(words,
            new Function<String, String>() {
                @Override
                public String apply(String item) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(item.substring(0,1).toUpperCase());
                    sb.append(item.substring(1));
                    return sb.toString();
                }
            });

        System.out.println(transformed);
    }
}
