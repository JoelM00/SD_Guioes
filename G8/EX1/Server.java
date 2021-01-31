package Guiao8.EX1;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    final static int WORKERS_PER_CONNECTION = 3;

    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(12345);

        while(true) {
            Socket s = ss.accept();
            FramedConnection c = new FramedConnection(s);

            for (int i = 0; i < WORKERS_PER_CONNECTION; ++i)
                new Thread(new ServerWorker(c)).start();
        }
    }
}
