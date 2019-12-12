package segmentedfilesystem;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main{


    public static void main(String[] args) throws IOException {
        byte[] buf = new byte[1028];

        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress address = InetAddress.getByName("csci-4409.morris.umn.edu");
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 6014);
            socket.send(packet);

            HashMap<Byte,clientFile> files = new HashMap<>();
            ArrayList<clientFile> allFiles = new ArrayList<>();

            while(true) {
                packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
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
