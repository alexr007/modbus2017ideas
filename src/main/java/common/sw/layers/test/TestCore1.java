package common.sw.layers.test;

import common.hw.comport.COMPort;
import common.hw.comport.COMPortProperties;
import common.hw.modbus.ModBus;
import common.hw.modbus.wad.WADdeviceType;
import common.sw.layers.ModBusChannels;
import common.sw.layers.ModBusDevices;
import common.sw.layers.ModBusEntities;
import constants.Ch;
import constants.Dv;
import constants.En;
import constants.Id;
import entities.EnCylinder;
import entities.EnValve;
import jssc.SerialPort;

/**
 * Created by alexr on 19.02.2017.
 */
public class TestCore1 {
    public void run(String[] args) throws Exception {
        // BUS initialization
        ModBusDevices devices = new ModBusDevices(
            new ModBus(
                new COMPort(
                    Dv.COM25,
                    new COMPortProperties(SerialPort.BAUDRATE_57600)
                )));
        // DEVICES initialization
        devices.add(Dv.DOS1, WADdeviceType.DOS, Id.x11 );
        devices.add(Dv.DOS2, WADdeviceType.DOS, Id.x12 );
        devices.add(Dv.DOS3, WADdeviceType.DOS, Id.x13 );
        devices.add(Dv.DOS4, WADdeviceType.DOS, Id.x14 );
        devices.add(Dv.DOS5, WADdeviceType.DOS, Id.x15 );
        devices.add(Dv.DI1, WADdeviceType.DI14, Id.x21 );
        devices.add(Dv.DI2, WADdeviceType.DI14, Id.x22 );
        devices.add(Dv.DI3, WADdeviceType.DI14, Id.x23 );
        devices.add(Dv.DI4, WADdeviceType.DI14, Id.x24 );
        devices.add(Dv.AIK1, WADdeviceType.AIK, Id.x31 );
        devices.add(Dv.AIK2, WADdeviceType.AIK, Id.x32 );
        devices.add(Dv.AO1, WADdeviceType.AO, Id.x41 );
        devices.add(Dv.AO2, WADdeviceType.AO, Id.x42 );
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
            devices.get("dev1").channel(1),
            devices.get("dev2").channel(1)
        );
        EnMotorPWM air3 = new EnMotorPWM(
            devices.get("dev1").channel(1),
            devices.get("dev2").channel(1)
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
