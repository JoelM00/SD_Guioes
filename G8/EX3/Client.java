package Guiao8.EX3;

import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket s = new Socket("localhost",12345);
        TaggedConnection t = new TaggedConnection(s);
        Demultiplexer d = new Demultiplexer(t);
        d.leitor();

        Thread[] ths = {
                new Thread(() -> {
                    try {
                        d.send(0, "Ola, ma Frend!".getBytes());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }),

                new Thread(() -> {
                    try {
                        d.send(0, " : ::: >>> ".getBytes());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }),

                new Thread(() -> {
                    try {
                        System.out.println("["+Thread.currentThread().getId()+"] -> 1");
                        d.send(1,"oiiiii boiii, tas a brincar!".getBytes());
                        byte[] f = d.receive(1);
                        System.out.println("["+Thread.currentThread().getId()+"] -> "+new String(f));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }),

                new Thread(() -> {
                    try {
                        System.out.println("[" + Thread.currentThread().getId() + "] -> 2");
                        d.send(2,"AEIOU".getBytes());
                        while (true) {
                            byte[] f = d.receive(2);
                            String res = new String(f);
                            if (res.length() == 0) break;
                            System.out.println("[" + Thread.currentThread().getId() + "] -> "+res);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }),

                new Thread(() -> {
                    try {
                        System.out.println("[" + Thread.currentThread().getId() + "] -> 4");
                        d.send(4,"ZZZZZ".getBytes());
                        while (true) {
                            byte[] f = d.receive(4);
                            String res = new String(f);
                            if (res.length() == 0) break;
                            System.out.println("[" + Thread.currentThread().getId() + "] -> "+res);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }),
        };

        for (Thread ts : ths) {
            ts.start();
        }

        for (Thread ts : ths) {
            ts.join();
        }
    }
}
