package G4.EX2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Barrier {
    private int cont = 0;
    private int max;
    private int epoca = 0;
    private boolean open = false;
    private ReentrantLock l = new ReentrantLock();
    private Condition c = l.newCondition();

    public Barrier(int max) {
        this.max = max;
    }

    public void verifica() throws InterruptedException {
        try {
            l.lock();
            cont++;
            int atual = epoca;

            if (cont < max) {
                while (cont < max && atual == epoca) {
                    c.await();
                }
            } else {
                c.signalAll();
                epoca++;
            }

            cont--;

        } finally {
            l.unlock();
        }
    }

    public void verificaV2() throws InterruptedException {
        try {
            l.lock();
            while (open) {
                c.await();
            }
            cont++;

            if (cont < max) {
                while (!open) {
                    c.await();
                }
            } else {
                c.signalAll();
                open = true;
            }

            cont--;

            if (cont == 0) {
                open = false;
                c.signalAll();
            }

        } finally {
            l.unlock();
        }
    }
}
