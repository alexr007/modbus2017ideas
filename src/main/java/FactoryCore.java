import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import common.hw.modbus.wad.*;
import common.sw.ModBusDevices;
import common.sw.ModBusChannels;
import common.sw.ModBusEntities;
import common.sw.ranges.Percentage;
import common.sw.ranges.RangeValues;
import common.BytesAsHex;
import common.hw.comport.COMPort;
import common.hw.comport.COMPortProperties;
import common.debug.PH;
import entities.Cylinder;
import entities.MotorPWM;
import entities.Valve;
import common.hw.modbus.command.MbData;
import common.hw.modbus.response.Values;
import common.sw.primitives.Word;
import constants.Ch;
import constants.En;
import constants.Id;
import constants.Dv;
import jssc.SerialPort;
import org.takes.http.Exit;
import org.takes.http.FtCli;
import common.hw.modbus.ModBus;
import web.tk.TkWebApp;
import _learn.mt.*;

import java.io.IOException;
import java.util.Arrays;

import static java.lang.Thread.sleep;

public class FactoryCore {
    /*
    WebServer
    TODO: relocate to different thread in future.
     */
    // WebServer sample
    public static void main1(String[] args) throws IOException {
        // start WebServer
        new FtCli(new TkWebApp(), args).start(Exit.NEVER);
    }


    // multi threading sample
    public static void main3(String[] args) {
        Store store=new Store();
        Producer producer = new Producer(store);
        Consumer consumer = new Consumer(store);
        new Thread(producer).start();
        new Thread(consumer).start();
    }

    // BytesAsHex
    public static void main4(String[] args) {
        MbData mbData = new MbData(new Word(65535));
        System.out.println(
            new BytesAsHex(mbData)
        );
    }

    // modbus
    public static void main5(String[] args) throws Exception {
        ModBus modBus = new ModBus(
            new COMPort(
                "COM25",
                new COMPortProperties(57600)
            )
        );
        System.out.println("port opened");

        long delta;
        long millis = System.currentTimeMillis();

        WAD_DOS_BUS dos = new WAD_DOS_BUS(modBus, 0x11);
        WAD_AO_BUS ao = new WAD_AO_BUS(modBus, 0x21);

        new PH("WAD-DOS-BUS temperature",dos.temperature());
        dos.channel(1).off();
        System.out.println(
            dos.channel(0).get()
        );
    }

    // Percentage
    public static void main6(String[] args) {
        Percentage percentage = new Percentage(new RangeValues(15000, 45000));
        System.out.println(percentage.get(25));
        Values.Single single = new Values.Single(1);
        Values.Multiple multiple = new Values.Multiple(new int[]{1, 2, 3});
        System.out.println(single);
        System.out.println(multiple);
    }

    public static void main7(String[] args) throws Exception {
        //Locker locker = new Locker();
        ModBus modBus = new ModBus(
            new COMPort(
                "COM25",
                new COMPortProperties(57600)
            )
        );

        ModBusDevices devices = new ModBusDevices(modBus);
        devices.add("01_DOS", WADdeviceType.DOS, 1 );
        ModBusAbstractDevice dev = devices.get("01_DOS");
        System.out.println(dev);
/*
        DecisionModule dm = new DecisionModule(100, devices) {
            private int counter = 0;

            @Override
            protected void task() {
                System.out.println("Thread:" + this.getClass() + ", val:" + counter++);
                if (counter == 100) { finish();}
            }
        };
        new Thread(dm).start();
        new Thread(new Thread1s(locker)).start();
        new Thread(new Thread2s(locker)).start();
        //new Thread(new Thread3s()).run();
        */
    }

    public static void main(String[] args) throws Exception {
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
            new Cylinder(
                new Valve(devices.get(Dv.DOS1).channel(1)),
                new Valve(devices.get(Dv.DOS1).channel(2))
                //new Valve(channels.get("chan3"))
            )
        );
        // ENTITIES initialization

        System.out.println(
            entities.get(En.Cylinder1)
        );
        ((Cylinder) entities.get(En.Cylinder1)).open();
        entities.getCylinder(En.Cylinder1).open();
        //cylinder.close();
        //cylinder.stop();
/*
        MotorPWM air1 = new MotorPWM(
            channels.get("AIR1"),
            channels.get("AIR1speed")
        );
        MotorPWM air2 = new MotorPWM(
            devices.get("dev1").channel(1),
            devices.get("dev2").channel(1)
        );
        MotorPWM air3 = new MotorPWM(
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

    public static void main8(String[] args) {
        System.out.println(
            new BytesAsHex(new byte[] {1,1,2,2,3,3})
        );
    }

    public static void main9(String[] args) {
        final Iterable<String> words = Arrays.asList("abc","cde","fxp","qwer","asdf","zxcv");
        final Iterable<String> transformed = Iterables.transform(words,
            new Function<String, String>() {
                @Override
                public String apply(String item) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(item.substring(0,1).toUpperCase());
                    sb.append(item.substring(1));
                    return sb.toString();
                }
            });

        System.out.println(transformed);
    }
}