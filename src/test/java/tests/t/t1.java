package tests.t;

import jbase.hex.HexFromByte;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

/**
 * Created by mac on 23.06.2017.
 */
public class t1 {

    public void run() {
/*
        System.out.println(
            new HexFromBytes(
                new byte[] {1, 2, 3, 4, 5}
            ).toString()

        );
*/


        byte[] bytess = {1, 2, 3, 4, 5};


        ByteBuffer wrapped = ByteBuffer.wrap(bytess);


        IntBuffer intBuffer = wrapped.asIntBuffer();
        int[] ints = intBuffer.array();

        Arrays.stream(ints).forEach(value -> System.out.println(
            new HexFromByte(value).toString()
        ));



/*
        iface[] ints = ByteBuffer.wrap(bytess)
            .order(ByteOrder.BIG_ENDIAN)
            .asIntBuffer().array();
*/


/*
        String s = Arrays.stream(ints)
            .mapToObj(value -> new HexFromByte(value).toString())
            .collect(Collectors.joining(", "));
        System.out.println(s);
*/
/*


        String collect = Arrays.asList()
            .stream()
            .map(i -> )
            .collect(Collectors.joining(", "));

        System.out.println(collect);

*/



/*
        System.out.println(
            Arrays.asList(1, 2, 3, 4, 5)
                .stream()
                .map(i -> new HexFromByte(i).toString())
                .collect(Collectors.joining(", "))
        );
*/


/*
        System.out.println(
            new StringJoiner(":", "[", "]")

        );
*/
    }
}
