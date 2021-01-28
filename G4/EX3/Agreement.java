package G4.EX3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Agreement {
    private int max;
    private int cont;
    private ReentrantLock l = new ReentrantLock();
    private Condition c = l.newCondition();
    private int maiorProposta;
    private int anterior;
    private int epoca = 0;

    public Agreement(int max) {
        this.max = max;
        cont = 0;
    }

    public int propoe(int x) throws InterruptedException {
        try {
            l.lock();
            int atual = epoca;
            cont++;
            maiorProposta = Math.max(maiorProposta,x);

            if (cont < max) {
                while (cont < max && epoca == atual) {
                    c.await();
                }
            } else {
                c.signalAll();
                epoca++;
                anterior = maiorProposta;
            }
            cont--;

            return anterior;

        } finally {
            l.unlock();
        }
    }
}
