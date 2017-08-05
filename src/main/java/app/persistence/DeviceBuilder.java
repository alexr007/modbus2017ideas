package app.persistence;

import app.persistence.init.dev.ModBusDevices;
import constants.Id;
import jbus.modbus.ModBus;
import org.javatuples.Triplet;
import java.util.ArrayList;
import java.util.Arrays;

import static constants.DevName.*;
import static constants.Id.*;
import static jwad.WadDevType.*;

/**
 * Created by alexr on 13.05.2017.
 */
public class DeviceBuilder {
    public static ModBusDevices buildEcoAlliance(ModBus bus) throws Exception {
        return
            new ModBusDevices(bus,
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
                )));
    }

    public static ModBusDevices buildTestEnvironment(ModBus bus) throws Exception {
        return
            new ModBusDevices(bus,
                new ArrayList<>(Arrays.asList(
                    new Triplet<>(DI1,  DI14, Id.x11),
                    new Triplet<>(DOS1, DOS,  Id.x31),
                    new Triplet<>(AO1,  AO6,  Id.x41)
                )));
    }
}
