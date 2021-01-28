package G1.EX2;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Bank b = new Bank();

        Thread[] ths = new Thread[10];

        for (int i = 0; i<10; i++) {
            ths[i] = new Thread(new Worker(b));
        }

        for (Thread t : ths) {
            t.start();
        }

        for (Thread t : ths) {
            t.join();
        }

        System.out.println(b.balance());
    }
}
