import java.io.Serializable;

public class Pheader implements Serializable {
    private long command; //8 bytes
    private int payloadSize; //4 bytes
    private String checksum; //8 bytes
    public Pheader(long command, int payloadSize, String checksum) {
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

    @Override
    public String toString() {
        return "Pheader{" +
                "command=" + command +
                ", payloadSize=" + payloadSize +
                ", checksum='" + checksum + '\'' +
                '}';
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }
}