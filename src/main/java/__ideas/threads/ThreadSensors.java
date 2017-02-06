package __ideas.threads;

import common.comport.COMPort;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

import static java.lang.Thread.sleep;

public class ThreadSensors implements Runnable {

    private final Object threadLocker = new Object();

    private class PortReader implements SerialPortEventListener {

        private final SerialPort serialPort;
        private byte[] readedData;

        public PortReader(SerialPort serialPort) {
            this.serialPort = serialPort;
        }

        // _____ single method, must implemented _____
        public synchronized void serialEvent(SerialPortEvent event)
        {
            synchronized (threadLocker) {
                if (event.isRXCHAR() & event.getEventValue()>0) {
                    try {
                        readedData = serialPort.readBytes(event.getEventValue());
                        threadLocker.notifyAll();
                    } catch (SerialPortException e1) {
                        System.out.println("COM port error");
                        e1.printStackTrace();
                    }
                }
            }
        }

        public byte[] data()
        {
            return readedData;
        }
    }

    @Override
    public void run() {
        int[] vals = {0b11111111,0b1,0b10,0b100,0b1000,0b10000,0b100000,0b1000000,0b10000000,0};

        try {
            COMPort com25 = new COMPort("COM25");
            System.out.println("port opened");

            long delta;
            long millis = System.currentTimeMillis();

/*
            PortReader pr = new PortReader(com25.port());

            for (int i = 0; i < 10; i++) {
                BytesOld cmd = new DOSWriteAll(i).get();
                System.out.println("written:"+cmd);
                BytesOld rd=new BytesOld(new int[]{0});
                synchronized (threadLocker) {
                    try {
                        com25.write(cmd, pr);
                        threadLocker.wait();
                        rd = new BytesOld(pr.get());
                        System.out.println("readed:"+rd);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // continue execution and sending get
//                    System.out.println("Sending get...");
                }
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            com25.port().removeEventListener();
*/

            delta = System.currentTimeMillis()-millis;
            System.out.println("delta:"+delta);

            com25.close();
            System.out.println("port closed");

        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }
}
