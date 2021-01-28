package G7;


import java.net.ServerSocket;
import java.net.Socket;

class ServerWorkerReader implements Runnable {
    private ServerSocket ss;
    private ContactList c;

    public ServerWorkerReader(ServerSocket ss,ContactList c) {
        this.c = c;
        this.ss = ss;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Socket s = ss.accept();
                new Thread(new WorkerReader(s,c)).start();
            }
        } catch (Exception e) {}
    }
}
