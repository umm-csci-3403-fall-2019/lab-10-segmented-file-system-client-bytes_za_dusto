package segmentedfilesystem;

import javax.xml.crypto.Data;
import java.net.DatagramPacket;
import java.util.HashMap;


public class clientFile {

    private int packetsCounter;
    private int packetsEncountered;
    private boolean done;
    private HashMap<Integer,byte[]> packets;

    public clientFile(DatagramPacket packet){
        packetsCounter = Integer.MAX_VALUE;
        packetsEncountered = 0;
        done = false;
        packets = new HashMap<>();
    }
}
