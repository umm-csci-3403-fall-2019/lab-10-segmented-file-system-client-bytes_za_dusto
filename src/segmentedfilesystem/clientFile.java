package segmentedfilesystem;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class clientFile {
    byte[] packet;
    byte fileId;
    byte status;
    byte[] packetNumber;

    int file0Size=-1;
    int file1Size=-1;
    int file2Size=-1;

    ArrayList<byte[]> file0 = new ArrayList<>();
    ArrayList<byte[]> file1 = new ArrayList<>();
    ArrayList<byte[]> file2 = new ArrayList<>();

    ArrayList<Byte> master = new ArrayList<>();


    public void writeFile() {

        if(!master.contains(this.fileId)) {
            master.add(this.fileId);
        }

        switch(master.indexOf(this.fileId)){
            case 0:
                file0.add(this.packet);
                if(this.status % 4 == 3){
                    file0Size = ByteBuffer.wrap(this.packetNumber).getShort()  + 2;
                }
                break;
            case 1:
                file1.add(this.packet);
                file1Size = ByteBuffer.wrap(this.packetNumber).getShort()  + 2;
                break;
            case 2:
                file2.add(this.packet);
                file2Size = ByteBuffer.wrap(this.packetNumber).getShort()  + 2;
                break;
        }
    }

    public boolean isDone() {
        return file0.size() == file0Size && file1.size() == file1Size && file2.size() == file2Size;
    }

}
