import java.io.IOException;
import java.net.Socket;

public class Sender {
    public static synchronized void sendMessageToOthers(Ppacket message,Nodes senderNode) throws IOException {
        for (Nodes node : Main.nodes) {
            if (!node.equals(senderNode)){
                node.sendMessage(message);
            }
        }
    }
    public static synchronized void sendMessageToAll(Ppacket message) throws IOException {
        System.out.println("tst04");
        for (Nodes node : Main.nodes) {
            System.out.println("tst03");
            node.sendMessage(message);
        }
    }
    public static synchronized void sendConnect(String ip) throws IOException {
        Main.nodes.add(new Nodes(new Socket(ip,6009)));
    }


}
