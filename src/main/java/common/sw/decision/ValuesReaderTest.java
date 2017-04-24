package common.sw.decision;

import common.hw.modbus.response.Values;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by alexr on 24.04.2017.
 */
public class ValuesReaderTest {
    public void test1() throws Exception {
        // этот пример показывает, как быстро получить значения всех необходимых переменных (сенсоров)
        ValuesMap valuesMap = new ValuesMap(new ValuesReader(
            Arrays.asList("A", "B", "C"),
            new FakeReader()
        ).get());
        // дальнейший доступ так:
        CharSequence key = "A";
        Values a = valuesMap.get(key);
        System.out.println(
            String.format("Key:%s, Value:%s",key, a)
        );
        // список всех ключей
        System.out.println(valuesMap.keySet());
        // проход по списку
        for (HashMap.Entry <CharSequence, Values> e: valuesMap.entrySet()) {
            System.out.println(
                String.format("Key:%s, Value:%s",e.getKey(),e.getValue())
            );
        }


    }
}
