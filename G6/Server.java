package G6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(12345);
        Calculator c = new Calculator();

        while (true) {
            Socket socket = ss.accept();
            new Thread(new ServerWorker(socket,c)).start();
        }
    }
}
