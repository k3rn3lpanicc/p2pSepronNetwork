import clientNode.Node;

import clientNode.data.Ppacket;
import clientNode.data.hasher.SHA3;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Base64;
import java.util.HashMap;

public class Main{
    public static byte[] objectToBytes(Object obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.flush();
        byte [] data = bos.toByteArray();
        return data;
    }
    public static void main(String[] args) throws IOException {
        HashMap<Integer ,Long> hashMap = new HashMap<>();
        hashMap.put(0 , 1L);
        hashMap.put(1 , 2L);
        hashMap.put(2 , 3L);
        //hashMap.put(3 , 4L);

        int data = 3;
        String strData = "3";
        Ppacket packetFromString = new Ppacket(1L , strData);
        Ppacket packetFromHashmap = new Ppacket(1L , objectToBytes(data));
        byte[] packetFromStringBytes = packetFromString.toBytes();
        byte[] packetFromHashmapBytes = packetFromHashmap.toBytes();
        String packetFromStringToString = packetFromString.toString();
        String packetFromHashmapToString = packetFromString.toString();

        System.out.println(packetFromStringToString.length() + " bytes");
        System.out.println(packetFromHashmapToString.length() + " bytes");

        System.out.println(packetFromStringBytes.length + " bytes");
        System.out.println(packetFromHashmapBytes.length + " bytes");

        Ppacket convertBytesToPacket = Ppacket.fromByteArray(packetFromStringBytes);
        System.out.println(convertBytesToPacket.describe());


        Node.start();
    }

}
