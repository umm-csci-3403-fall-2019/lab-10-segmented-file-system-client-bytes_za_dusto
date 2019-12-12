package segmentedfilesystem;

import java.io.*;
import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;


public class ClientFile {
    private int maxPackets;
    private int packetsEncountered;
    private boolean finished;
    private HashMap<Integer,byte[]> packetsMap;

    public ClientFile(DatagramPacket packet){
        maxPackets = Integer.MAX_VALUE;
        packetsEncountered = 0;
        finished = false;
        packetsMap = new HashMap<>();

        this.addPacket(packet);
    }

    public void addPacket(DatagramPacket packet) {

        packetsEncountered++;

        int status = packet.getData()[0];
        byte[] pNumberData = new byte[]{packet.getData()[2], packet.getData()[3]};
        int packetNumber = ByteBuffer.wrap(pNumberData).getShort();


        switch(status % 4){
            case 3: {
                // If it's the last packet, get the packet number and add 2 for header plus index shift
                maxPackets = packetNumber + 2;
            }
            case 1: {
                // If it's a data packet, put it in the hashmap with it's packetNumber as key
                packetsMap.put(packetNumber, Arrays.copyOf(packet.getData(),packet.getLength()));
                break;
            }
            default:{
                // Data packets start at 0, so header packet is stored in hashmap with -1 as key
                packetsMap.put(-1,Arrays.copyOf(packet.getData(),packet.getLength()));
            }
        }

        // If packets encountered is the size we got from the last packet, file is finished receiving all apckets
        if(packetsEncountered==maxPackets){
            finished = true;
        }
    }

    // Create the File
    public void createFile() throws IOException {

        byte[] headerFile = packetsMap.get(-1);
        String fileName = new String(headerFile,2,headerFile.length-2);

        File file = new File(fileName);
        OutputStream fileOS = new FileOutputStream(file);
        System.out.println(fileName);


        for(int i=0; i <maxPackets - 1; i++){
            byte[] packet = packetsMap.get(i);
            fileOS.write(packet,4,packet.length-4);
        }
    }

    // Return if clientFile is finished
    public boolean isFinished(){
        return finished;
    }


}
