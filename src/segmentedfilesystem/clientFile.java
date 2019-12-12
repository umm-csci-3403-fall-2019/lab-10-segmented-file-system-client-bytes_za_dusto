package segmentedfilesystem;

import java.io.*;
import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;


public class clientFile {
    private int maxPackets;
    private int packetsEncountered;
    private boolean finished;
    private HashMap<Integer,byte[]> packetsMap;

    public clientFile(DatagramPacket packet){
        maxPackets = Integer.MAX_VALUE;
        packetsEncountered = 0;
        finished = false;
        packetsMap = new HashMap<>();

        this.addPacket(packet);
    }

    public void addPacket(DatagramPacket packet) {

        packetsEncountered++;

        int status = packet.getData()[0];
        byte[] pNumberData = new byte[]{packet.getData()[2], packet.getData()[2]};
        int packetNumber = ByteBuffer.wrap(pNumberData).getShort();


        switch(status % 4){
            case 3: {
                maxPackets = packetNumber + 2;
            }
            case 1: {
                packetsMap.put(packetNumber, Arrays.copyOf(packet.getData(),packet.getLength()));
                break;
            }
            default:{
                packetsMap.put(-1,Arrays.copyOf(packet.getData(),packet.getLength()));
            }
        }

        if(packetsEncountered==maxPackets){
            finished = true;
        }
    }

    public void createFile() throws IOException {

        byte[] headerFile = packetsMap.get(-1);
        String fileName = new String(headerFile,2,headerFile.length-2);

        File file = new File(fileName);
        OutputStream fileOS = new FileOutputStream(file);
        System.out.println(fileName);

        if(file.createNewFile()){
            System.out.println("File with same name already exists");
        }

        for(int i=0; i <maxPackets - 1; i++){
            byte[] packet = packetsMap.get(i);
            fileOS.write(packet,4,packet.length-4);
        }
    }


    public boolean isFinished(){
        return finished;
    }


}
