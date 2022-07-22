import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class AppCommunicator implements Runnable{
    private ServerSocket appSocket = new ServerSocket(8005);
    public HashSet<ConnectedApps> connectedApps = new HashSet<>();

    public AppCommunicator() throws IOException {
    }


    @Override
    public void run() {
        while (true){
            try {

                ConnectedApps connectedApp = new ConnectedApps(appSocket.accept());
                connectedApps.add(connectedApp);
                new Thread(connectedApp).start();

                ///del
                System.out.println("app connected");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
