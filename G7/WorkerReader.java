package G7;

import java.io.*;
import java.net.Socket;

public class WorkerReader implements Runnable {
    private final Socket s;
    private ContactList c;
    private DataInputStream in;

    public WorkerReader(Socket socket, ContactList contactList) throws IOException {
        this.s = socket;
        this.c = contactList;
        this.in = new DataInputStream(new BufferedInputStream(s.getInputStream()));
    }

    @Override
    public void run() {
        try (s) {
            while (true) {
                c.addContact(in);
            }
        } catch (Exception e) {
            System.out.println(" -> Conexao terminada!");
        }
    }
}
