package G7;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientStub {
    private Socket s;
    private DataInputStream in;
    private DataOutputStream out;

    public ClientStub(int porta) throws IOException {
        this.s = new Socket("localhost",porta);
        this.in = new DataInputStream(new BufferedInputStream(s.getInputStream()));
        this.out = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
    }

    public void addContact(Contact c) throws IOException {
        c.serialize(out);
    }

    public List<Contact> getContacts() throws IOException {
        int size = in.readInt();
        List<Contact> res = new ArrayList<>();
        for (int i = 0; i<size; i++) {
            res.add(Contact.deserialize(in));
        }
        return res;
    }
}
