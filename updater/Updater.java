import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

class ClientHandler implements Runnable{
    Socket clientSocket;
    public ClientHandler(Socket clienSocket){
        this.clientSocket = clienSocket;
    }

    public void run(){
        try {
            int request = clientSocket.getInputStream().read();
            if(request == 1){
                //todo : update the whole project
                if(Updater.isUnix() || Updater.isWindows()){
                    ProcessBuilder builder = new ProcessBuilder();
                    if (Updater.isWindows()) {
                        builder.command("cmd.exe", "/c", "dir");
                    } else {
                        builder.command("sh", "-c", "ls");
                    }
                    builder.directory(new File(System.getProperty("user.home")));
                    Process process = builder.start();
                    StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
                    Executors.newSingleThreadExecutor().submit(streamGobbler);
                    int exitCode = process.waitFor();
                    assert exitCode == 0;


                }else if(Updater.isWindows()){

                }else if(Updater.isMac()){

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
public class Updater {
    private static String OS = System.getProperty("os.name").toLowerCase();
    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(14341);
        while(true){
            Socket clientSocket = listener.accept();
        }
    }


    public static boolean isWindows() {
        return (OS.indexOf("win") >= 0);
    }

    public static boolean isMac() {
        return (OS.indexOf("mac") >= 0);
    }

    public static boolean isUnix() {
        return (OS.indexOf("nix") >= 0
                || OS.indexOf("nux") >= 0
                || OS.indexOf("aix") > 0);
    }

    public static boolean isSolaris() {
        return (OS.indexOf("sunos") >= 0);
    }

}
