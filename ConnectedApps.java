import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import org.json.JSONException;
import org.json.JSONObject;

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
    public void praseCommand(String jsonString) throws JSONException {
        JSONObject json = new JSONObject(jsonString);
       out.println(json.getString("command"));
    }
    @Override
    public void run() {
        String stringFromApp="";
        int croshe = 0;
        while(appSocket.isConnected()){
            try {
                InputStream inputStream = appSocket.getInputStream();
                DataInputStream dataInputStream = new DataInputStream(inputStream);
                //String message = String.valueOf(dataInputStream.read());
               // System.out.println("The message sent from the socket was: " + message);
//                int size =(dataInputStream.read())-48;
//                out.println("message size is: "+size);
//                String gotBytes = "";
//                for (int i = 0; i < size; i++) {
//                    gotBytes+= new DataInputStream(appSocket.getInputStream()).read();
//                }
//                out.println(gotBytes);
                int msg =(dataInputStream.read());
                stringFromApp += (char) msg;
                if(msg==123){
                    croshe+=1;
                }else if(msg==125){
                    croshe-=1;
                    if(croshe==0){
                        ///got string
                        praseCommand(stringFromApp);
                        ///reset
                        stringFromApp="";
                    }
                }
            }catch (Exception e){
                disConnect();
                break;
            }
        }
    }
}
