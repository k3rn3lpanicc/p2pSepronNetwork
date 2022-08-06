import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Nodes implements Runnable {
     Socket nodeSocket;
    public Nodes(Socket nodeSocket) throws IOException {
        this.nodeSocket = nodeSocket;
        outputStream = new ObjectOutputStream(nodeSocket.getOutputStream());

        System.out.println("connected to "+nodeSocket.getRemoteSocketAddress().toString());
    }
    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;
    public void sendMessage(Ppacket message) throws IOException {
        if (nodeSocket.isConnected()) {
            outputStream.writeObject(message);
            System.out.println("tst07");
        }
    }
    public void disConnect(){
        Main.nodes.remove(this);
        System.out.println("disconnected from "+nodeSocket.getRemoteSocketAddress().toString());
        if(!Main.requestingThread.isAlive()){
            Main.requestingThread.start();
        }else{
            Main.requestingThread = new Thread(new RequestingThread());
            //thread was running!
        }
    }
    public void praseMessage(Ppacket message) throws IOException {
        System.out.println("tst06");
        if (message.getCommand()==0){
            System.out.println(message.describe());
            Sender.sendMessageToOthers(message,this);
            for (ConnectedApps app: Main.appCommunicator.connectedApps){
                if(app.shouldGet){
                    app.send(message);
                }
            }
        }
    }

    @Override
    public void run() {
      /*  try {
            sendMessage(new Ppacket(0,"hi"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        try {
            inputStream =  new ObjectInputStream(nodeSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (nodeSocket.isConnected()) {
            try{
                Ppacket ppacket = (Ppacket) inputStream.readObject();
                praseMessage(ppacket);
            }
            catch (Exception e){
                disConnect();
                break;
            }
        }
    }
}
