import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class Main {

    public static HashSet<Nodes> nodes = new HashSet<>();
    public static Thread requestingThread = new Thread(new RequestingThread());
    public static final int MAX_CONNECTED_NODE=4;
    public static AppCommunicator appCommunicator;

    public static void main(String[] args) {
        try {
            appCommunicator = new AppCommunicator();
            new Thread(appCommunicator).start();
            ServerSocket serverSocket = new ServerSocket(6009);
          // Sender.sendConnect("185.110.191.125");
            //sendMessageToAll(new Ppacket(0,"hi"));
            while (true) {
                Socket client;
                client = serverSocket.accept();
                Nodes userHandler = new Nodes(client);
                nodes.add(userHandler);
                new Thread(userHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
