package segmentedfilesystem;

import java.util.Arrays;

public class HeaderPacket {
    byte status;
    byte fileId;
    byte[] filename;

    public  HeaderPacket(byte[] p){
        this.status = p[0];
        this.fileId = p[1];
        this.filename = Arrays.copyOfRange(p,2,p.length);
    }
}
