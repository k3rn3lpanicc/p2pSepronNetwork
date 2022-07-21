package sepronP2P.src;


import java.net.Socket;

public class Nodes implements Runnable {
    private Socket nodeSocket;
    public Nodes(Socket nodeSocket) {
        this.nodeSocket = nodeSocket;
    }
    public void disConnect(){
        Main.nodes.remove(this);
        if(!Main.requestingThread.isAlive()){
            Main.requestingThread.start();
        }else{
            Main.requestingThread = new Thread(new RequestingThread());
            //thread was running!
        }
    }

    @Override
    public void run() {
        while (nodeSocket.isConnected()) {
            try{

            }
            catch (Exception e){
                disConnect();
                break;
            }
        }
    }
}
