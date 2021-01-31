package Guiao8.EX2;

import java.net.Socket;

public class Client {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("localhost", 12345);
        TaggedConnection c =  new TaggedConnection(s);

        // send request
        c.send(1, "Ola".getBytes());

        // One-way
        c.send(0, ":-p".getBytes());

        // get reply
        TaggedConnection.Frame f = c.receive();

        System.out.println("Reply: " + new String(f.dados));

        // Get stream of messages until empty msg
        c.send(2, "ABCDE".getBytes());
        while (true) {
            f = c.receive();
            if (f.dados.length == 0) break;
            System.out.println("From stream: " + new String(f.dados));
        }

        c.close();
    }
}