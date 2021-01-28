package G4.EX2;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Barrier b = new Barrier(3);

        for (int i = 0; i<9; i++) {
            Thread.sleep(500);
            new Thread(() -> {
                try {
                    b.verificaV2();
                    System.out.println(Thread.currentThread().getId()+" -> Done!");
                } catch (Exception ignored) { }
            }).start();
        }
    }
}
