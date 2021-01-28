package G7;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerWorkerWriter implements Runnable {
    private ServerSocket ss;
    private ContactList c;

    public ServerWorkerWriter(ServerSocket ss,ContactList c) {
        this.c = c;
        this.ss = ss;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Socket s = ss.accept();
                new Thread(new WorkerWriter(s,c)).start();
            }
        } catch (Exception e) {}
    }
}
