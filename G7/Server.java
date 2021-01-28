package G7;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    public static void main (String[] args) throws IOException {
        ServerSocket ss1 = new ServerSocket(12345);
        ServerSocket ss2 = new ServerSocket(54321);
        ContactList contactList = new ContactList();

        new Thread(new ServerWorkerReader(ss1,contactList)).start();
        new Thread(new ServerWorkerWriter(ss2,contactList)).start();
    }
}
