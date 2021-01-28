package G1.EX2;

public class Worker implements Runnable {
    private Bank b;

    public Worker(Bank b) {
        this.b = b;
    }

    @Override
    public void run() {
        for (int i = 0; i<1000; i++) {
            b.deposit(100);
        }
    }
}
