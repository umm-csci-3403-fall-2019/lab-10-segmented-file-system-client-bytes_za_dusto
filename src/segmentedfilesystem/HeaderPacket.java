package segmentedfilesystem;
import java.util.Arrays;

public class HeaderPacket extends Packet{

    byte status;
    byte fileId;
    byte[] fileName;

    public HeaderPacket(byte[] packet){
        this.status=packet[0];
        this.fileId=packet[1];
        this.fileName = Arrays.copyOfRange(packet,2,packet.length);
    }
}
