import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import org.json.JSONException;
import org.json.JSONObject;

import static java.lang.System.out;

public class ConnectedApps implements Runnable{
    private Socket appSocket;
    public boolean shouldGet=false;
    public ConnectedApps(Socket appSocket) {
        this.appSocket = appSocket;
    }
    public void send(Ppacket message) throws IOException {
        DataOutputStream outputStream = new DataOutputStream(appSocket.getOutputStream());
        if (appSocket.isConnected()) {
            outputStream.write(message.toJson().getBytes());
        }
    }
    public void sendString(String message) throws IOException {
        DataOutputStream outputStream = new DataOutputStream(appSocket.getOutputStream());
        if (appSocket.isConnected()) {
            outputStream.write(message.getBytes(StandardCharsets.UTF_8));
        }
    }
    public void disConnect(){
        Main.appCommunicator.connectedApps.remove(this);

        ///del
        out.println("app disconnected!");
    }
    public void praseCommand(String jsonString) throws JSONException, IOException {
        JSONObject json = new JSONObject(jsonString);
        String command =json.getString("command");
        out.println(command);
        shouldGet= !command.equals("0") && (command.equals("1") || shouldGet);
        if(command.equals("sendMessage")){
            Main.sendMessageToAll(new Ppacket((long) Integer.parseInt(json.getString("pPacketCommand")), json.getString("pPacketPayLoad")));
        }
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
