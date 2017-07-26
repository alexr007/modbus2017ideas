package tests.iterables;

import com.google.common.collect.Iterables;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by alexr on 19.02.2017.
 */
public class Test_Iterables {
    public static void main(String[] args) {
        final Iterable<String> words = Arrays.asList("abc","cde","fxp","qwer","asdf","zxcv");

        // iterables
        System.out.println(
            Iterables.transform(words,
                item -> String.format("%s%s",
                    item.substring(0,1).toUpperCase(),
                    item.substring(1)
                ))
        );

        // auto
        System.out.println(
            StreamSupport.stream(words.spliterator(), false)
                .map(item -> String.format("%s%s",
                    item.substring(0, 1).toUpperCase(),
                    item.substring(1)
            )).collect(Collectors.toList())
        );


    }
}
