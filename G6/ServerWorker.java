package G6;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerWorker implements Runnable {
    private Socket s;
    private Calculator c;

    public ServerWorker(Socket s,Calculator c) {
        this.s = s;
        this.c = c;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream());

            String line;
            while ((line = in.readLine()) != null) {
                String[] tokens = line.split(" ", 2);
                int x = 0;
                if (!tokens[0].equals("m")) {
                    x = Integer.parseInt(tokens[1]);
                }
                int res = 0;

                try {
                    c.l.lock();

                    switch (tokens[0]) {
                        case "+" -> res = c.sum(x);
                        case "-" -> res = c.sub(x);
                        case "*" -> res = c.mul(x);
                        case "/" -> res = c.div(x);
                        case "m" -> res = c.media();
                    }

                } finally {
                    c.l.unlock();
                }
                out.println(line);
                out.println(res);
                out.flush();
            }

            s.shutdownOutput();
            s.shutdownInput();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
