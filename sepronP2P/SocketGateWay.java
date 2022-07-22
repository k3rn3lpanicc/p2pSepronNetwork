package sepronP2P.src;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketGateWay {
    private Socket socket;
    private ByteArrayOutputStream outputStream;
    private ByteArrayInputStream inputStream;

    public SocketGateWay(Socket socket) throws IOException {
        this.socket = socket;
        //todo
    }
}
