package segmentedfilesystem;

import java.net.DatagramPacket;
import java.util.Arrays;

public class HeaderPacket {
    byte status;
    byte fileId;
    String filename;

    public  HeaderPacket(DatagramPacket p){
        this.status = p.getData()[0];
        this.fileId = p.getData()[1];
        this.filename = new String(Arrays.copyOfRange(p.getData(),2,p.getLength()));
    }
}
