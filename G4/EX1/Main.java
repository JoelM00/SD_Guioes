package G4.EX1;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Barrier b = new Barrier(3);

        for (int i = 0; i<4; i++) {
            Thread.sleep(1000);
            new Thread(() -> {
                try {
                    b.verifica();
                    System.out.println(Thread.currentThread().getId()+" -> Done!");
                } catch (Exception ignored) { }
            }).start();
        }
    }
}
