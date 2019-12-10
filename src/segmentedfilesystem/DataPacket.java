package segmentedfilesystem;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class DataPacket {
    byte status;
    byte fileId;
    byte[] packetNumber = new byte[2];
    byte[] data;

    public DataPacket(DatagramPacket p){
        this.status = p.getData()[0];
        this.fileId = p.getData()[1];
        this.packetNumber[0] = p.getData()[2];
        this.packetNumber[1] = p.getData()[3];
        this.data = Arrays.copyOfRange(p.getData(),4,p.getLength());
    }
}
