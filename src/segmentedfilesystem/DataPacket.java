package segmentedfilesystem;

import java.util.Arrays;

public class DataPacket {
    byte status;
    byte fileId;
    byte[] packetNumber;
    byte[] data;

    public DataPacket(byte[] p){
        this.status = p[0];
        this.fileId = p[1];
        this.packetNumber = new byte[]{p[2], p[3]};
        this.data = Arrays.copyOfRange(p,4,p.length);
    }
}
