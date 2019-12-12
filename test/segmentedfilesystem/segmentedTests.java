package segmentedfilesystem;

import static org.junit.Assert.*;

import org.junit.Test;

import java.net.*;
import java.nio.ByteBuffer;


public class segmentedTests {

    @Test
    public void headerPacketTest() {

        byte[] buffer = new byte[10];
        buffer[0] = 2;

        DatagramPacket packet = new DatagramPacket(buffer,buffer.length);
        ClientFile cf = new ClientFile(packet);
        // hashmap has a key of -1 for a header packet
        assertNotNull(cf.packetsMap.get(-1));
    }

    @Test
    public void DataPacketTest() {

        byte[] buffer = new byte[10];
        buffer[0] = 5;
        buffer[1] = 49;
        buffer[2] = 8;
        buffer[3] = 9;

        byte[] pNumberData = new byte[]{8, 9};
        int packetNumber = ByteBuffer.wrap(pNumberData).getShort();

        DatagramPacket packet = new DatagramPacket(buffer,buffer.length);
        ClientFile cf = new ClientFile(packet);

        // hashmap has a key with the packetnumber of the packet
        assertNotNull(cf.packetsMap.get(packetNumber));
    }

    @Test
    public void MaxPacketsTest() {

        byte[] buffer = new byte[10];
        buffer[0] = 3;
        buffer[1] = 42;
        buffer[2] = 8;
        buffer[3] = 9;

        byte[] pNumberData = new byte[]{8, 9};
        int packetNumber = ByteBuffer.wrap(pNumberData).getShort();

        // It captures the maxpackets for the last packet in a file(status 3%4)
        DatagramPacket packet = new DatagramPacket(buffer,buffer.length);
        ClientFile cf = new ClientFile(packet);
        assertEquals(cf.maxPackets,packetNumber+2);
    }

    @Test
    public void FinishedTest() {

        byte[] buffer = new byte[10];
        buffer[0] = 3;
        buffer[1] = 42;
        buffer[2] = 8;
        buffer[3] = 9;


        DatagramPacket packet = new DatagramPacket(buffer,buffer.length);
        ClientFile cf = new ClientFile(packet);
        // Make sure finished status is false by default
        assertFalse(cf.finished);
    }




}
