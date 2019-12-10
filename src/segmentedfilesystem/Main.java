package segmentedfilesystem;
import com.sun.xml.internal.ws.api.message.HeaderList;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

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

            while(!cf.isDone()) {
                packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                if (packet.getData()[0] % 2 == 0) {
                    System.out.println("Header Packet");
                    HeaderPacket headerPacket = new HeaderPacket(packet.getData());
                    cf.addPacket(headerPacket);
                } else {
                    System.out.println("Data Packet");
                    DataPacket dataPacket = new DataPacket(packet.getData());
                    cf.addPacket(dataPacket);
                }

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
