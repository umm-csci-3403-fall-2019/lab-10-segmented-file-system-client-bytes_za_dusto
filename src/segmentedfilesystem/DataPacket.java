package segmentedfilesystem;

import java.util.Arrays;

public class DataPacket extends Packet {
    byte status;
    byte fileId;
    byte[] packetNum;
    byte[] data;

    public DataPacket(byte[] packet){
        this.status=packet[0];
        this.fileId=packet[1];
        this.packetNum=Arrays.copyOfRange(packet,2,4);
        this.data = Arrays.copyOfRange(packet,4,packet.length);
    }
}
