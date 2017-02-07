import __ideas.ranges.Percentage;
import __ideas.ranges.RangeValues;
import common.BytesAsHex;
import common.comport.COMPort;
import common.comport.COMPortProperties;
import common.debug.PH;
import common.hardware.Cylinder;
import common.hardware.MotorPWM;
import common.hardware.Valve;
import common.hardware.port.AOPort;
import common.hardware.port.Channel;
import common.hardware.port.DRPort;
import common.modbus.command.MbData;
import common.modbus.device.DeviceProperties;
import common.device.Device__;
import common.modbus.device.PortType;
import common.modbus.device.SignalType;
import common.modbus.response.InvalidModBusResponse;
import common.modbus.wad.ModBusInvalidFunction;
import common.modbus.wad.WAD_AO_BUS;
import common.modbus.wad.WAD_DOS_BUS;
import common.primitives.Word;
import jssc.SerialPortException;
import org.takes.http.Exit;
import org.takes.http.FtCli;
import persistence.ModBus;
import web.tk.TkWebApp;
import _learn.mt.*;

import java.io.IOException;

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

    public static void main2(String[] args) {
        DeviceProperties ai4 = new DeviceProperties(SignalType.Analog, PortType.Input, 4);
        DeviceProperties ai16 = new DeviceProperties(SignalType.Analog, PortType.Input, 16);
        DeviceProperties ao4 = new DeviceProperties(SignalType.Analog, PortType.Output, 4);
        DeviceProperties ao6 = new DeviceProperties(SignalType.Analog, PortType.Output, 6);

        DeviceProperties di8 = new DeviceProperties(SignalType.Digital, PortType.Input, 8);
        DeviceProperties di14 = new DeviceProperties(SignalType.Digital, PortType.Input, 14);
        DeviceProperties do4 = new DeviceProperties(SignalType.Digital, PortType.Output, 4);
        DeviceProperties do8 = new DeviceProperties(SignalType.Digital, PortType.Output, 8);
        DeviceProperties do12 = new DeviceProperties(SignalType.Digital, PortType.Output, 12);
        System.out.println(ai4);
        System.out.println(di14);

        Device__ i1 = new Device__(1, "WAD-AIK-BUS", ai4, "4separate");
        Device__ o11 = new Device__(0x11, "WAD-DOS12-BUS", do12, "12common");
        System.out.println(i1);
        System.out.println(o11);

    }

    // multi threading sample
    public static void main3(String[] args) {
        Store store=new Store();
        Producer producer = new Producer(store);
        Consumer consumer = new Consumer(store);
        new Thread(producer).start();
        new Thread(consumer).start();

    }

    public static void main4(String[] args) {
        MbData mbData = new MbData(new Word(65535));
        System.out.println(
            new BytesAsHex(mbData)
        );
    }

    public static void main5(String[] args) throws SerialPortException, InvalidModBusResponse, ModBusInvalidFunction, InterruptedException {
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

        Cylinder cylinder = new Cylinder(
            new Valve(new DRPort(dos, new Channel(1))),
            new Valve(new DRPort(dos, new Channel(2)))
        );

        cylinder.open();
        sleep(1000);
        cylinder.close();
        sleep(1000);
        cylinder.stop();

        MotorPWM air1 = new MotorPWM(
            new DRPort(dos, new Channel(1)),
            new AOPort(ao, new Channel(1)));
        MotorPWM air2 = new MotorPWM(
            new DRPort(dos, new Channel(2)),
            new AOPort(ao, new Channel(2)));
        MotorPWM air3 = new MotorPWM(
            new DRPort(dos, new Channel(3)),
            new AOPort(ao, new Channel(3)));
        air1.off();
        air2.off();
        air3.off();
        air1.on();
        air2.on();
        air3.on();
        air1.run(1000);
        air2.run(2000);
        air3.run(3000);


/*
        for (int i = 1; i <= 4; i++) {
            DOS.channel(i).off();
        }

        Values values = DOS.channel(0).get();
        System.out.println(values.single());
        System.out.println(values.get(1));
        System.out.println(values.get(2));
        System.out.println(values.get(3));
        System.out.println(values.get(4));
        System.out.println(values.get(5));
        System.out.println(values.get(6));
        System.out.println(values.get(7));
        System.out.println(values.get(8));
*/

        //DOS.channel(0).set(new int[]{1,0,0,0,0,0,0,0});

        //WAD_Channel chan = dos.channel(1);

        //new PH("get",chan.get());
        //chan.on();
        //chan.fail();
        //new PH("opened",chan.opened());
        //new PH("closed",chan.closed());
        //chan.on();
        //chan.off();
        //chan.set(100);




        delta = System.currentTimeMillis() - millis;
        System.out.println("delta:" + delta);
        modBus.close();
        System.out.println("port closed");
    }

    public static void main(String[] args) {
        Percentage percentage = new Percentage(new RangeValues(15000, 45000));
        System.out.println(percentage.get(25));
    }

}
