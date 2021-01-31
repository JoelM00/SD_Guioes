package Guiao8.EX3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(12345);

        while (true) {
            Socket s = ss.accept();
            TaggedConnection t = new TaggedConnection(s);

            for (int i = 0; i<3; i++) {
                new Thread(new ServerWorker(t)).start();
            }
        }
    }
}
