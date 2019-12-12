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
            HashMap<Byte,ClientFile> files = new HashMap<>();
            ArrayList<ClientFile> allFiles = new ArrayList<>();

            while(true) {
                // Receive packets
                packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                // capture the second index of buffer which is fileId
                byte fileId = buf[1];

                // If fieldId is not in hashmap meaning it has never been encountered
                //  add it to hashmap. Otherwise, get it from hashmap and call addPacket
                if(files.get(fileId)==null){
                    files.put(fileId, new ClientFile(packet));
                } else {
                    files.get(fileId).addPacket(packet);
                }

                // See if file associated with fileId is finished.
                // Then remove it from hashmap and add it arraylist
                if(files.get(fileId).isFinished()){
                    allFiles.add(files.remove(fileId));
                    // If hashmap is empty, all files are finished.
                    if(files.isEmpty()){
                        break;
                    }
                }
            }

            // For every file in array list create the file
            for(ClientFile file: allFiles){
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
