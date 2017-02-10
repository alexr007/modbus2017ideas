package common.hw.modbus.response;

import common.hw.modbus.command.MbCRC;
import common.hw.modbus.MbResponse;

/**
 * Created by alexr on 21.01.2017.
 */
public class RsParsed {
    private final byte[] response;
    private final int length;
    private boolean crcChecked = false;
    private boolean crcValid = false;
    private boolean lengthChecked = false;
    private boolean lengthValid = false;
    private final int byteID = 0; // 1st byte of response
    private final int byteCMD = 1; // 2nd byte of response
    private final int byteLEN = 2; // 3rd byte of response
    private final int byteDATA = 3; // 4rd byte of response
    public static int cmdRead = 0x03;
    public static int cmdWrite = 0x10;

    public RsParsed(MbResponse response) {
        this(response.get());
    }

    public RsParsed(byte[] response) {
        this.response = response;
        this.length=response.length;
    }

    public boolean valid() throws InvalidModBusResponseCRC, InvalidModBusResponseLength {
        if (!crcChecked) {
            crcValid =new MbCRC(response).valid();
            crcChecked =true;
        }
        if (!lengthChecked) {
            lengthValid=(length==(3+response[byteLEN]+2));
            lengthChecked =true;
        }
        if (crcValid&&lengthValid) return true;
        else if (!crcValid) throw new InvalidModBusResponseCRC();
        else throw new InvalidModBusResponseLength();
    }

    public int device() throws InvalidModBusResponseCRC, InvalidModBusResponseLength {
        if (valid()) {
            return response[byteID];
        }
        else return 0;
    }

    public int command() throws InvalidModBusResponseCRC, InvalidModBusResponseLength {
        if (valid()) {
            return response[byteCMD];
        }
        else return 0;
    }

    public int length() throws InvalidModBusResponseCRC, InvalidModBusResponseLength {
        if (valid()) {
            return response[byteLEN];
        }
        else return 0;
    }

//    public byte[] data() throws InvalidModBusResponseCRC, InvalidModBusResponseLength {
    public int[] data() throws InvalidModBusResponseCRC, InvalidModBusResponseLength {
        if (valid()) {
            int[] result = new int[length()];
            for (int i = byteDATA; i < byteDATA+length(); i++) {
                result[i-byteDATA]=response[i];
            }
            return result;
            //return Arrays.copyOfRange(response, 3, 3+length());
        }
        else return new int[]{};
    }
}
