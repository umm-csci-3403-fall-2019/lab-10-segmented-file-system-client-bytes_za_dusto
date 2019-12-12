package segmentedfilesystem;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main{


    public static void main(String[] args) throws IOException {
        byte[] buf = new byte[1028];

        try {
            // Establish Connection
            DatagramSocket socket = new DatagramSocket();
            InetAddress address = InetAddress.getByName("csci-4409.morris.umn.edu");
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 6014);
            socket.send(packet);

            // A Hashmap to store files with their fileId as key
            //  and a list of all the files in an array list
            HashMap<Byte,clientFile> files = new HashMap<>();
            ArrayList<clientFile> allFiles = new ArrayList<>();

            while(true) {
                // Receive packets
                packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                // capture the second index of buffer which is fileId
                byte fileId = buf[1];

                if(files.get(fileId)==null){
                    files.put(fileId, new clientFile(packet));
                } else {
                    files.get(fileId).addPacket(packet);
                }

                if(files.get(fileId).isFinished()){
                    allFiles.add(files.remove(fileId));
                    if(files.isEmpty()){
                        break;
                    }
                }
            }

            for(clientFile file: allFiles){
                file.createFile();
            }

        } catch (SocketException se){
            System.out.println("Failed to create a socket");
            System.out.println(se);
        } catch (UnknownHostException ue) {
            System.out.println("Failed to find a host");
            System.out.println(ue);
        }

    }

}
