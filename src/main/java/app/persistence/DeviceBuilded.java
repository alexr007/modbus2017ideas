package app.persistence;

import app.persistence.init.ModBusDevices;
import constants.Dv;
import constants.Id;
import jbus.modbus.ModBus;
import jwad.WadDevType;
import org.javatuples.Triplet;

import java.util.ArrayList;

/**
 * Created by alexr on 13.05.2017.
 */
public class DeviceBuilded {
    public static ArrayList<Triplet<String, WadDevType, Integer>>  buildEcoAlliance()  {
        return
            new ArrayList<Triplet<String, WadDevType, Integer>>() {{
                add(new Triplet<>(Dv.AIK1, WadDevType.AIK, Id.x21));
                add(new Triplet<>(Dv.AIK2, WadDevType.AIK, Id.x22));

                add(new Triplet<>(Dv.AO1, WadDevType.AO6, Id.x41));
                add(new Triplet<>(Dv.AO2, WadDevType.AO6, Id.x42));

                add(new Triplet<>(Dv.DI1, WadDevType.DI14, Id.x11));
                add(new Triplet<>(Dv.DI2, WadDevType.DI14, Id.x12));
                add(new Triplet<>(Dv.DI3, WadDevType.DI14, Id.x13));
                add(new Triplet<>(Dv.DI4, WadDevType.DI14, Id.x14));

                add(new Triplet<>(Dv.DOS1, WadDevType.DOS, Id.x31));
                add(new Triplet<>(Dv.DOS2, WadDevType.DOS, Id.x32));
                add(new Triplet<>(Dv.DOS3, WadDevType.DOS, Id.x33));
                add(new Triplet<>(Dv.DOS4, WadDevType.DOS, Id.x34));
                add(new Triplet<>(Dv.DOS5, WadDevType.DOS, Id.x35));
            }};
    }

    public static ArrayList<Triplet<String, WadDevType, Integer>> buildTestEnvironment() {
        return new ArrayList<Triplet<String, WadDevType, Integer>>() {{
                add(new Triplet<>(Dv.DI1, WadDevType.DI14, Id.x11));
                add(new Triplet<>(Dv.DOS1, WadDevType.DOS, Id.x31));
                add(new Triplet<>(Dv.DOS2, WadDevType.DOS, Id.x32));
                add(new Triplet<>(Dv.DOS3, WadDevType.DOS, Id.x33));
                add(new Triplet<>(Dv.AO1, WadDevType.AO6, Id.x41));
                add(new Triplet<>(Dv.AIK2, WadDevType.AIK, Id.x22));
            }};
    }
}
