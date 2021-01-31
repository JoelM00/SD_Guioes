package Guiao8.EX2;

public class ServerWorker implements Runnable {
    private final TaggedConnection c;

    public ServerWorker(TaggedConnection c) {
        this.c = c;
    }
    @Override
    public void run() {
        try (c) {
            while (true) {
                TaggedConnection.Frame frame = c.receive();
                int tag = frame.tag;
                String data = new String(frame.dados);

                if (frame.tag == 0)
                    System.out.println("Got one-way: " + data);

                else if (frame.tag % 2 == 1) {
                    System.out.println("Replying to: " + data);
                    c.send(frame.tag, data.toUpperCase().getBytes());

                } else {
                    for (int i = 0; i < data.length(); ++i) {
                        String str = data.substring(i, i+1);
                        System.out.println("Streaming: " + str);
                        c.send(tag, str.getBytes());
                        Thread.sleep(100);
                    }
                    c.send(tag, new byte[0]);
                }
            }
        } catch (Exception ignored) { }
    }
}
