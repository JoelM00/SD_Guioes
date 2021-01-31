package Guiao8.EX1;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

public class FramedConnection implements Closeable {
    private Socket s;
    private DataInputStream in;
    private DataOutputStream out;
    private ReentrantLock wl = new ReentrantLock();
    private ReentrantLock rl = new ReentrantLock();


    public FramedConnection(Socket s) throws IOException {
        this.s = s;
        this.in = new DataInputStream(new BufferedInputStream(s.getInputStream()));
        this.out = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
    }

    public void send(byte[] dados) throws IOException {
        try {
            wl.lock();
            out.writeInt(dados.length);
            out.write(dados);
            out.flush();
        } finally {
            wl.unlock();
        }
    }

    public byte[] receive() throws IOException {
        try {
            rl.lock();
            int size = in.readInt();
            byte[] bytes = new byte[size];
            in.readFully(bytes);
            return bytes;
        } finally {
            rl.unlock();
        }
    }

    public void close() throws IOException {
        this.s.close();
    }
}
