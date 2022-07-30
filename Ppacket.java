
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Base64;


public class Ppacket {
    Pheader pheader;
    private byte[] payload; //'payloadSize' bytes
    public Ppacket(long command, byte[] payload) {
        this.pheader = new Pheader(command,payload.length, SHA3.getSHA3(payload).substring(0,9));
        this.payload = payload;
    }

    public Ppacket(long command, String payload) {
        this(command,payload.getBytes());
    }

    private static byte[] longtoBytes(long data) {
        return new byte[]{
                (byte) ((data >> 56) & 0xff),
                (byte) ((data >> 48) & 0xff),
                (byte) ((data >> 40) & 0xff),
                (byte) ((data >> 32) & 0xff),
                (byte) ((data >> 24) & 0xff),
                (byte) ((data >> 16) & 0xff),
                (byte) ((data >> 8) & 0xff),
                (byte) ((data >> 0) & 0xff),
        };
    }
    public byte[] toBytes(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byteArrayOutputStream.write(longtoBytes(pheader.getCommand()));
            byteArrayOutputStream.write(pheader.getPayloadSize());
            byteArrayOutputStream.write(pheader.getChecksum().getBytes());
            byteArrayOutputStream.write(payload);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public String toString(){
        return Base64.getEncoder().encodeToString(toBytes());
    }
    public String describe(){
        return "Ppacket{" +
                "pheader=" + pheader +
                ", payload=" + new String(payload) +
                '}';
    }


    private static long bytesToLong(byte[] bytes){
        return new BigInteger(bytes).longValue();
    }
    public static Ppacket fromByteArray(byte[] data) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        try {
            long command = bytesToLong(inputStream.readNBytes(8));
            int payloadSize = inputStream.read();
            String checkSum = new String(inputStream.readNBytes(9));
            byte[] payload = inputStream.readNBytes(payloadSize);
            return new Ppacket(command,payload);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Ppacket fromString(String st){
        return fromByteArray(Base64.getDecoder().decode(st));
    }
    public Ppacket(){}
    public byte[] getPayload() {
        return payload;
    }
    public void setPayload(byte[] payload) {
        this.payload = payload;
    }
    public boolean isHealthy(){
        return SHA3.getSHA3(this.payload).substring(0,9).equals(pheader.getChecksum());
    }
    public Pheader getHeader() {
        return pheader;
    }
}