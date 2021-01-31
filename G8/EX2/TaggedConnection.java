package Guiao8.EX2;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

public class TaggedConnection implements Closeable {
    public class Frame {
        int tag;
        byte[] dados;

        public Frame(int tag,byte[] dados) {
            this.tag = tag;
            this.dados = dados;
        }
    }

    private Socket s;
    private DataInputStream in;
    private DataOutputStream out;
    private ReentrantLock rl = new ReentrantLock();
    private ReentrantLock wl = new ReentrantLock();

    public TaggedConnection(Socket s) throws IOException {
        this.s = s;
        this.in = new DataInputStream(new BufferedInputStream(s.getInputStream()));
        this.out = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
    }

    public void send(Frame f) throws IOException {
        this.send(f.tag,f.dados);
    }

    public void send(int tag,byte[] dados) throws IOException {
        try {
            wl.lock();
            out.writeInt(tag);
            out.writeInt(dados.length);
            out.write(dados);
            out.flush();
        } finally {
            wl.unlock();
        }
    }

    public Frame receive() throws IOException {
        try {
            rl.lock();
            int tag = in.readInt();
            int size = in.readInt();
            byte[] dados = new byte[size];
            in.readFully(dados);
            return new Frame(tag,dados);
        } finally {
            rl.unlock();
        }
    }

    public void close() throws IOException {
        this.s.close();
    }
}
