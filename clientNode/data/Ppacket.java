package clientNode.data;

import clientNode.data.hasher.SHA3;

class Header{
    private long command; //8 bytes
    private int payloadSize; //4 bytes
    private String checksum; //8 bytes
    public Header(long command, int payloadSize, String checksum) {
        this.command = command;
        this.payloadSize = payloadSize;
        this.checksum = checksum;
    }
    public long getCommand() {
        return command;
    }
    public void setCommand(long command) {
        this.command = command;
    }
    public int getPayloadSize() {
        return payloadSize;
    }
    public void setPayloadSize(int payloadSize) {
        this.payloadSize = payloadSize;
    }
    public String getChecksum() {
        return checksum;
    }
    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }
}
public class Ppacket {
    Header header;
    private byte[] payload; //'payloadSize' bytes
    public Ppacket(long command, int payloadSize, String checksum, byte[] payload) {
        this.header = new Header(command,payloadSize,checksum);
        this.payload = payload;
    }
    public Ppacket(){}
    public byte[] getPayload() {
        return payload;
    }
    public void setPayload(byte[] payload) {
        this.payload = payload;
    }
    public boolean isHealthy(){
        return SHA3.getSHA3(this.payload).substring(0,8).equals(header.getChecksum());
    }
}
