package app.persistence;

import constants.DevName;
import constants.Id;
import jwad.WadDevType;
import org.javatuples.Triplet;
import java.util.ArrayList;
import java.util.Arrays;

import static constants.DevName.*;
import static constants.Id.*;
import static jwad.WadDevType.*;

/**
 * Created by alexr on 13.05.2017.
 */
public class DeviceBuilded {
    public static ArrayList<Triplet<DevName, WadDevType, Integer>>  buildEcoAlliance()  {
        return
            new ArrayList<>(Arrays.asList(
                new Triplet<>(AIK1, AIK, x21),
                new Triplet<>(AIK2, AIK, x22),

                new Triplet<>(AO1, AO6, x41),
                new Triplet<>(AO2, AO6, x42),

                new Triplet<>(DI1, DI14, x11),
                new Triplet<>(DI2, DI14, x12),
                new Triplet<>(DI3, DI14, x13),
                new Triplet<>(DI4, DI14, x14),

                new Triplet<>(DOS1, DOS, x31),
                new Triplet<>(DOS2, DOS, x32),
                new Triplet<>(DOS3, DOS, x33),
                new Triplet<>(DOS4, DOS, x34),
                new Triplet<>(DOS5, DOS, x35)
            ));
    }

    public static ArrayList<Triplet<DevName, WadDevType, Integer>> buildTestEnvironment() {
        return
            new ArrayList<>(Arrays.asList(
                new Triplet<>(DI1, DI14, Id.x11),
                new Triplet<>(DOS1, DOS, Id.x31),
                new Triplet<>(DOS2, DOS, Id.x32),
                new Triplet<>(DOS3, DOS, Id.x33),
                new Triplet<>(AO1, AO6, Id.x41),
                new Triplet<>(AIK2, AIK, Id.x22)
            ));
    }
}
