package app.decision.test;

import constants.ChanName;
import jbase.IterableToString;
import jbase.hex.HexFromBytes;
import jbase.hex.HexFromWord;
import jbase.hex.HexFromWords;
import jbus.modbus.response.Values;
import org.javatuples.Pair;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public static void test2() {
        System.out.println(
            new Values.Multiple(new int[]{9,8,7,6})
        );
    }

}
