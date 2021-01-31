package Guiao8.EX3;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Demultiplexer {
    public class Buffer {
        Queue<byte[]> mensagens = new ArrayDeque<>();
        Condition c = l.newCondition();
    }

    public Buffer get(int tag) {
        Buffer b = this.buffers.get(tag);
        if (b == null) {
            b = new Buffer();
            this.buffers.put(tag,b);
        }
        return b;
    }

    private TaggedConnection t;
    private Map<Integer,Buffer> buffers;
    private ReentrantLock l;

    public Demultiplexer(TaggedConnection t) {
        this.t = t;
        this.buffers = new HashMap<>();
        this.l = new ReentrantLock();
    }

    public void leitor() {
        new Thread(() -> {
            try {
                while (true) {
                    TaggedConnection.Frame f = t.receive();
                    try {
                        l.lock();
                        Buffer b = get(f.tag);
                        b.mensagens.add(f.dados);
                        b.c.signalAll();
                    } finally {
                        l.unlock();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void send(int tag,byte[] dados) throws IOException {
        this.t.send(tag,dados);
    }

    public byte[] receive(int tag) throws InterruptedException {
        try {
            l.lock();
            Buffer b = get(tag);

            while (b.mensagens.isEmpty()) {
                b.c.await();
            }

            return b.mensagens.poll();
        } finally {
            l.unlock();
        }
    }




}
