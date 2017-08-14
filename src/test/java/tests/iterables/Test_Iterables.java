package tests.iterables;

import com.google.common.collect.Iterables;
import java.util.Arrays;
import java.util.List;
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

        // auto stream
        System.out.println(
            StreamSupport.stream(words.spliterator(), false)
                .map(item ->
                    item.substring(0, 1).toUpperCase()+
                    item.substring(1)
            ).collect(Collectors.toList())
        );

        List<String> words1 = Arrays.asList("abc", "cde", "fxp", "qwer", "asdf", "zxcv");
        System.out.println(
            words1.stream()
                .map(item ->
                    item.substring(0, 1).toUpperCase()+
                        item.substring(1))
                .collect(Collectors.toList())
        );

    }
}
