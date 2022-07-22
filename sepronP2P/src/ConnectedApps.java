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
                //String message = String.valueOf(dataInputStream.read());
               // System.out.println("The message sent from the socket was: " + message);
                int size =(dataInputStream.read())-48;
                out.println("message size is: "+size);
                String gotBytes = "";
                for (int i = 0; i < size; i++) {
                    gotBytes+= (char)new DataInputStream(appSocket.getInputStream()).read();
                }
                out.println(gotBytes);

            }catch (Exception e){
                disConnect();
                break;
            }
        }
    }
}
