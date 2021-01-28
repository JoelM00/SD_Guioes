package G4.EX1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Barrier {
    private int cont = 0;
    private int max;
    private ReentrantLock l = new ReentrantLock();
    private Condition c = l.newCondition();

    public Barrier(int max) {
        this.max = max;
    }

    public void verifica() throws InterruptedException {
        try {
            l.lock();
            cont++;

            while (cont < max) {
                c.await();
            }

            c.signalAll();
        } finally {
            l.unlock();
        }
    }
}
