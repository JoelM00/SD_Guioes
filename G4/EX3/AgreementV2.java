package G4.EX3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class AgreementV2 {
    private class Epoca {
        int maiorProp;
    }

    private int max;
    private ReentrantLock l = new ReentrantLock();
    private Condition c = l.newCondition();
    private Epoca epoca;
    private int cont = 0;

    public AgreementV2(int max) {
        this.max = max;
        epoca = new Epoca();
    }

    public int propoeV2(int x) throws InterruptedException {
        try {
            l.lock();
            Epoca e = this.epoca;
            e.maiorProp = Math.max(e.maiorProp,x);
            cont++;

            if (cont < max) {
                while (cont < max && e == this.epoca) {
                    c.await();
                }
            } else {
                c.signalAll();
                this.epoca = new Epoca();
                cont = 0;
            }

            return e.maiorProp;

        } finally {
            l.unlock();
        }
    }

}
