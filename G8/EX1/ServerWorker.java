package Guiao8.EX1;


public class ServerWorker implements Runnable {
    private final FramedConnection f;

    public ServerWorker(FramedConnection f) {
        this.f = f;
    }

    @Override
    public void run() {
        try (f) {
            while (true) {
                byte[] b = f.receive();
                String msg = new String(b);
                System.out.println("["+Thread.currentThread().getId()+"] Replying to: " + msg);
                f.send(msg.toUpperCase().getBytes());
            }
        } catch (Exception ignored) { }
    }
}
