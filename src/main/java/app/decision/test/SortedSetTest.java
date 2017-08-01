package app.decision.test;

import constants.ChanName;
import jbase.IterableToString;
import jbase.hex.HexFromBytes;
import jbase.hex.HexFromWord;
import jbase.hex.HexFromWords;
import jbus.modbus.response.Values;
import org.javatuples.Pair;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by mac on 25.07.2017.
 */
public class SortedSetTest {
    public static void test1() {
        EnumSet<ChanName> stop1 = EnumSet.of(ChanName.STOP_1, ChanName.STOP_2);
        final EnumSet set = EnumSet.allOf(ChanName.class);

        System.out.println(set);

        SortedSet<Integer> ints = new TreeSet<>(Arrays.asList(
            11, 2, 7, 4, 5, 1
        ));

        ints.forEach((item)->{
            System.out.println(String.format("Item:%s",item.toString()));
        });
    }

    public static ArrayList<Pair<String, Integer>> source() {
        return
            new ArrayList<>(Arrays.asList(
                new Pair<>("A", 1),
                new Pair<>("AA", 11),
                new Pair<>("AAA", 111),
                new Pair<>("B", 2),
                new Pair<>("BB", 22),
                new Pair<>("BBB", 222),
                new Pair<>("C", 3),
                new Pair<>("CC", 33),
                new Pair<>("CCC", 333)
            ));
    }

    public static void test3() {
        System.out.println(
            source()
                .stream()
                .filter( (item)-> item.getValue0().length()==2)
                .map(item -> String.format("{K:%s, V:%s}", item.getValue0(), item.getValue1() ))
                .collect(Collectors.toList())
                .toString()
        );
    }

    public static void test4() {
        System.out.println(
            source()
                .stream()
                .filter( (item)-> item.getValue0().length() ==3)
                .map(item -> new HexFromWord(item.getValue1()))
                .collect(Collectors.toList())
                .toString()
        );
    }

    public static void test21() {
        String[][] src = {{"a", "b"}, {"c", "d"}, {"e", "f"}};

        System.out.println(
            Arrays.stream(src)
                //.filter(s -> s.length==1)
                .filter(x-> Arrays.asList(x).contains("a"))
                .map(x -> Arrays.asList(x).toString())
                .filter(s -> s.contains(","))
                //.filter(x -> "a".equals(x.toString()))
                .collect(Collectors.toList())
                .toString()
        );
    }

    public static void test22() {
        String[][] src = {{"a", "b"}, {"c", "d"}, {"e", "f"}};

        System.out.println(
            Arrays.stream(src)
                .flatMap(x -> Arrays.stream(x))
                //.filter(s -> s.contains("a"))
                //.filter(x-> x. Arrays.asList(x).contains("a"))
                //.map(x -> Arrays.asList(x).toString())
                //.filter(s -> s.contains(","))
                //.filter(x -> "a".equals(x.toString()))
                .collect(Collectors.toList())
                .toString()
        );
    }

    public static void test23() {
        String[][] src = {{"a", "bb"}, {"ccc", "dddd"}, {"eeeee", "ffffff"}};

        System.out.println(
            Arrays.stream(src)
                .flatMap(x -> Arrays.stream(x))
                .mapToInt(x -> x.length())
                .sorted()
                .summaryStatistics()
                //.filter(s -> s.contains("a"))
                //.filter(x-> x. Arrays.asList(x).contains("a"))
                //.map(x -> Arrays.asList(x).toString())
                //.filter(s -> s.contains(","))
                //.filter(x -> "a".equals(x.toString()))
                //.collect(Collectors.toList())
                //.toString()
        );
    }
    public static void test24() {
        String[][] src = {{"a", "bb"}, {"c", "dd"}, {"e", "fff"}};

        System.out.println(
            Arrays.stream(src)
                .flatMap(x -> Arrays.stream(x))
                .map(x -> String.format("L:%s",x.length()))
            //.filter(s -> s.contains("a"))
            //.filter(x-> x. Arrays.asList(x).contains("a"))
            //.map(x -> Arrays.asList(x).toString())
            //.filter(s -> s.contains(","))
            //.filter(x -> "a".equals(x.toString()))

          //.collect(Collectors.toList())
                .collect(Collectors.joining("_"))
            //.toString()
        );
    }

    public static void test251() {
        System.out.println(
            new HexFromWords(new int[]{1,2,3,4, (short)0xFFFF})
                .toString()
        );
        System.out.println(
            new HexFromWords(new short[]{1,2,3,4, (short)0xFFFF})
                .toString()
        );
    }

    public static void test252() {
        System.out.println(
            new HexFromBytes(new byte[]{1,2,3,4, (byte)0xFF})
                .toString()
        );
    }

    public static void test26() {
        System.out.println(
            new IterableToString<Integer>(new int[]{1, 2, 3, 5}, integer -> String.format("_%s_", integer.toString()))
        );
    }

    public static void test27() {
        System.out.println(
            new Values.Multiple(new int[]{9,8,7,6})
        );
    }

    public static void test28() {
            List<PersonTest> list = new ArrayList<PersonTest>() {{
                add(new PersonTest(100, "Mohan"));
                add(new PersonTest(200, "Sohan"));
                add(new PersonTest(300, "Mahesh"));
            }};

            Map<Integer, String> map = list.stream()
                .collect(Collectors.toMap(
                    person -> person.getId(), person -> person.getName()));

            map.forEach((x, y) -> System.out.println("Key: " + x +", value: "+ y));
    }

    public static void test29() {
        Values.Multiple ints = new Values.Multiple(new int[]{10, 20, 30, 40, 50, 60, 70});
/*
        List<Integer> set = new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(3);
        }};

        Map<Integer, Integer> collect = set.stream()
            .collect(Collectors.toMap(
                o -> o,
                o -> ints.get(o)
            ));
        collect.forEach((k,v)-> System.out.println(
            String.format(
                "k:%sv:%s",k,v
            )
        ));
*/
        Map<Integer, Integer> collect = IntStream.range(1,ints.count()+1).boxed()
            //.forEach(p-> System.out.println(p));
        .collect(Collectors.toMap(
            i -> i,
            j -> ints.get(j)*2
        ));
        collect.forEach((k,v)-> System.out.println(
            String.format(
                "k:%sv:%s",k,v
            )));




/*
        IntStream.range(0, ints.count())
            .collect(Collectors.toMap(
                new Function<Object, Object>() {
                    @Override
                    public Object apply(Object o) {
                        return null;
                    }
                },
                new Function<Object, Object>() {
                    @Override
                    public Object apply(Object o) {
                        return null;
                    }
                }
            ))
*/
/*
            .forEach(
                p-> System.out.println(
                    String.format("K:%d,V:%d",
                        p+1,
                        ints.get(p+1)
                )
*/



    }

    public static void test230() {
        //3 apple, 2 banana, others 1
        List<Item> items = Arrays.asList(
            new Item("apple",      10, new BigDecimal("9.99")),
            new Item("banana",     20, new BigDecimal("19.99")),
            new Item("orang",      10, new BigDecimal("29.99")),
            new Item("watermelon", 10, new BigDecimal("29.99")),
            new Item("papaya",     20, new BigDecimal("9.99")),
            new Item("apple",      10, new BigDecimal("9.99")),
            new Item("banana",     10, new BigDecimal("19.99")),
            new Item("apple",      20, new BigDecimal("9.99"))
        );

        //group by price
        Map<BigDecimal, List<Item>> groupByPriceMap =
            items.stream().
                collect(Collectors.groupingBy(item -> item.price()));

        System.out.println(groupByPriceMap);

        // group by price, uses 'mapping' to convert List<Item> to Set<String>
        Map<BigDecimal, Set<String>> result =
            items.stream().
                collect(
                    Collectors.groupingBy(Item::price,
                        Collectors.mapping(Item::name, Collectors.toSet())
                )
            );

        System.out.println(result);
    }

    public static void test31() {
        //3 apple, 2 banana, others 1
        List<String> items =
            Arrays.asList("apple", "apple", "banana",
                "apple", "orange", "banana", "papaya");

        Map<String, Long> result =
            items.stream().collect(
                Collectors.groupingBy(
                    Function.identity(), Collectors.counting()
                )
            );

        System.out.println(result);


    }

    public static void test2() {
        Stream.of("one", "two", "three", "four")
                     .filter(e -> e.length() > 3)
                     .peek(e -> System.out.println("Filtered value: " + e))
                     .map(String::toUpperCase)
                     .peek(e -> System.out.println("Mapped value: " + e))
                     .collect(Collectors.toList());

    }

    public static void test331() {
        Map<Integer, List<String>> collect = Stream.of("one", "two", "three", "four")
            .collect(Collectors.groupingBy(s -> s.length()));
        System.out.println(collect);
    }

    public static void test332() {
        Map<Integer, Map<Boolean, Map<Boolean, Map<Boolean, List<String>>>>> collect = Stream.of("one", "two", "three", "four", "five", "six")
            .collect(
                Collectors.groupingBy(s -> s.length(),
                    Collectors.groupingBy(s -> s.contains("o"),
                        Collectors.groupingBy(s -> s.startsWith("t"),
                            Collectors.groupingBy(s -> s.endsWith("e"),
                                Collectors.toList()
                            )))));
        System.out.println(collect);
    }

    public static void test333() {
        Map<Integer, List<String>> collect = Stream.of("one", "two", "three", "four", "five", "six")
            .collect(
                Collectors.groupingBy(s -> s.length()
                )
            );
        System.out.println(collect);
    }

    public static void test33() {
        System.out.println(
            Stream.of("one", "two", "three", "four", "five", "six")
                .collect(
                    Collectors.toMap(
                        o -> String.format("%s%s",o.substring(0,1).toUpperCase(),o.substring(1)),
                        String::length
                    )
                )
        );
    }

}
