package G7;

import java.io.*;
import java.net.Socket;

public class WorkerWriter implements Runnable {
    private final Socket s;
    private ContactList c;
    private DataOutputStream out;

    public WorkerWriter(Socket socket, ContactList contactList) throws IOException {
        this.s = socket;
        this.c = contactList;
        this.out = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
    }

    @Override
    public void run() {
        try (s) {
            c.getContacts(out);
        } catch (Exception e) {
            System.out.println(" -> Conexao terminada!");
        }
    }
}
