package segmentedfilesystem;

import java.io.IOException;
import java.net.*;

public class Main{

    int port; // call in Sys.in
    InetAddress address;
    DatagramSocket socket = null;
    DatagramPacket packet;
    byte[] sendBuf = new byte[1028];

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.out.println("Pass in a port");
            return;
        }

        try {
            DatagramSocket socket = new DatagramSocket();

            byte[] buf = new byte[1028];
            InetAddress address = InetAddress.getByName("csci-4409.morris.umn.edu");
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 6014);
            socket.send(packet);

            clientFile cf = new clientFile();



        } catch (SocketException se){
            System.out.println("Failed to create a socket");
            System.out.println(se);
        } catch (UnknownHostException ue) {
            System.out.println("Failed to find a host");
            System.out.println(ue);
        }

    }

}
