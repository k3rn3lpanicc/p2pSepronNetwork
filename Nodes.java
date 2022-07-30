import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Nodes implements Runnable {
    private Socket nodeSocket;
    public Nodes(Socket nodeSocket) {
        this.nodeSocket = nodeSocket;
        System.out.println("connected to "+nodeSocket.getRemoteSocketAddress().toString());
    }
    public void sendMessage(Ppacket message) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(nodeSocket.getOutputStream());
        if (nodeSocket.isConnected()) {
            outputStream.writeObject(message);
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
        if (message.pheader.getCommand()==0){
            System.out.println(message.describe());
            Main.sendMessageToOthers(message,this);
        }
    }

    @Override
    public void run() {
        while (nodeSocket.isConnected()) {
            try{
                ObjectInputStream inputStream = new ObjectInputStream(nodeSocket.getInputStream());
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
