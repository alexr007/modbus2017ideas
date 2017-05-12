package app.persistence.test;

import jbus.comport.COMPort;
import jbus.comport.COMPortProperties;
import jbus.modbus.ModBus;
import jwad.WadDevType;
import app.persistence.init.ModBusChannels;
import app.persistence.init.ModBusDevices;
import app.persistence.ModBusEntities;
import constants.Ch;
import constants.Dv;
import constants.En;
import constants.Id;
import entities.EnCylinder;
import entities.EnValve;
import jssc.SerialPort;
import org.javatuples.Triplet;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by alexr on 26.04.2017.
 */
public class ModBusDevicesTest {

    public void test() throws Exception {

        ModBusDevices devices = new ModBusDevices(
            new ModBus(
                new COMPort(
                    Dv.COM25,
                    new COMPortProperties(SerialPort.BAUDRATE_57600)
                )
            ),
            new ArrayList<Triplet<String, WadDevType, Integer>>() {{
                add(new Triplet<>("DEV1", WadDevType.AIK, Id.x11));
                add(new Triplet<>("DEV2", WadDevType.AO6, Id.x12));
                add(new Triplet<>("DEV3", WadDevType.DI, Id.x13));
                add(new Triplet<>("DEV4", WadDevType.DI14, Id.x14));
                add(new Triplet<>("DEV5", WadDevType.DOS, Id.x15));
            }}
        );
        System.out.println(
            devices.toString()
        );
/*
        WAD_AIK_BUS aik1 = (WAD_AIK_BUS) devices.get("DEV1");
        aik1.channel(aa).
*/
        devices.finish();
    }

    public void test2() throws Exception {
        ModBusDevices devices = new ModBusDevices(
            new ModBus(
                new COMPort(
                    Dv.COM25,
                    new COMPortProperties(SerialPort.BAUDRATE_57600)
                )
            ),
            new File("devices.xmlDir")
        );
    }

    public void test_old_ugly_configuration() throws Exception {
        // BUS initialization
        ModBusDevices devices = new ModBusDevices(
            new ModBus(
                new COMPort(
                    Dv.COM25,
                    new COMPortProperties(SerialPort.BAUDRATE_57600)
                )));
        // DEVICES initialization
        devices.add(Dv.DOS1, WadDevType.DOS, Id.x11 );
        devices.add(Dv.DOS2, WadDevType.DOS, Id.x12 );
        devices.add(Dv.DOS3, WadDevType.DOS, Id.x13 );
        devices.add(Dv.DOS4, WadDevType.DOS, Id.x14 );
        devices.add(Dv.DOS5, WadDevType.DOS, Id.x15 );
        devices.add(Dv.DI1, WadDevType.DI14, Id.x21 );
        devices.add(Dv.DI2, WadDevType.DI14, Id.x22 );
        devices.add(Dv.DI3, WadDevType.DI14, Id.x23 );
        devices.add(Dv.DI4, WadDevType.DI14, Id.x24 );
        devices.add(Dv.AIK1, WadDevType.AIK, Id.x31 );
        devices.add(Dv.AIK2, WadDevType.AIK, Id.x32 );
        devices.add(Dv.AO1, WadDevType.AO, Id.x41 );
        devices.add(Dv.AO2, WadDevType.AO, Id.x42 );
        ModBusChannels channels = new ModBusChannels(devices);
        // CHANNELS initialization
        channels.add(En.T1, Dv.DOS1, Ch.n1);
        channels.add(En.T2, Dv.DOS1, Ch.n2);
        channels.add(En.Transporter1, Dv.DOS1, Ch.n4);
        channels.add(En.Transporter2, Dv.DOS1, Ch.n5);
        channels.add(En.Transporter2speed, Dv.DOS1, Ch.n6);
        // work begins here !
/*
        Values t1 = channels.get(En.T1).get();
        Values t2 = channels.get(En.T2).get();
        channels.get(En.Transporter1).off();
        channels.get(En.Transporter2).on();
*/
        ModBusEntities entities = new ModBusEntities(channels);
        entities.add(En.Cylinder1,
            new EnCylinder(
                new EnValve(devices.get(Dv.DOS1).channel(1)),
                new EnValve(devices.get(Dv.DOS1).channel(2))
                //new EnValve(channels.get("chan3"))
            )
        );
        // ENTITIES initialization

        System.out.println(
            entities.get(En.Cylinder1)
        );

        ((EnCylinder)entities.get(En.Cylinder1)).open();
        //entities.getCylinder(En.Cylinder1).open();
        //cylinder.close();
        //cylinder.stop();
/*
        EnMotorPWM air1 = new EnMotorPWM(
            channels.get("AIR1"),
            channels.get("AIR1speed")
        );
        EnMotorPWM air2 = new EnMotorPWM(
            devices.get("dev1").channel(aa),
            devices.get("dev2").channel(aa)
        );
        EnMotorPWM air3 = new EnMotorPWM(
            devices.get("dev1").channel(aa),
            devices.get("dev2").channel(aa)
        );
        air1.off();
        air2.off();
        air3.off();
        air1.on();
        air2.on();
        air3.on();
        air1.run(1000);
        air2.run(2000);
        air3.run(3000);
*/




        devices.finish();
    }
}
