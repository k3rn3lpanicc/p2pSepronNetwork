package sepronP2P.src;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import static java.lang.System.out;

public class ConnectedApps implements Runnable{
    private Socket appSocket;
    public boolean shouldGet=false;
    public ConnectedApps(Socket appSocket) {
        this.appSocket = appSocket;
    }
    public void disConnect(){
        Main.appCommunicator.connectedApps.remove(this);

        ///del
        out.println("app disconnected!");
    }

    @Override
    public void run() {
        while(appSocket.isConnected()){
            try {
                InputStream inputStream = appSocket.getInputStream();
                DataInputStream dataInputStream = new DataInputStream(inputStream);
                String message = String.valueOf(dataInputStream.read());
                System.out.println("The message sent from the socket was: " + message);


            }catch (Exception e){
                disConnect();
                break;
            }
        }
    }
}
