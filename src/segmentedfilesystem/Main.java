package segmentedfilesystem;
import java.io.IOException;
import java.net.*;

public class Main{

    int port; // call in Sys.in
    InetAddress address;
    DatagramSocket socket = null;
    DatagramPacket packet;
    byte[] sendBuf = new byte[256];

    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.out.println("Pass in a port followed by a hostname");
            return;
        } else {
            System.out.println("Using port " + args[0] + " and using hostname " + args[1]);
        }

        try {
            DatagramSocket socket = new DatagramSocket();

            byte[] buf = new byte[256];
            InetAddress address = InetAddress.getByName(args[1]);
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, Integer.parseInt(args[0]));
            socket.send(packet);

            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);

            Packet.addPacket(packet);

        } catch (SocketException se){
            System.out.println("Failed to create a socket");
            System.out.println(se);
        } catch (UnknownHostException ue) {
            System.out.println("Failed to find a host");
            System.out.println(ue);
        }

    }

}
