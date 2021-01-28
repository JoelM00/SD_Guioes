package G5.EX2;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class Warehouse {
    private Map<String, Product> m =  new HashMap<>();
    private ReentrantLock l = new ReentrantLock();

    private class Product {
        int q = 0;
        Condition c = l.newCondition();
    }

    private Product get(String s) {
        Product p = m.get(s);
        if (p != null) return p;
        p = new Product();
        m.put(s, p);
        return p;
    }

    public void supply(String s, int q) {
        try {
            l.lock();
            Product p = get(s);
            p.q += q;
            p.c.signalAll();
        } finally {
            l.unlock();
        }
    }


    public void consume(String[] a) throws InterruptedException {
        Product p;
        try {
            l.lock();
            for (int i = 0; i<a.length; i++) {
                p = get(a[i]);
                while (p.q == 0) {
                    i = 0;
                    p.c.await();
                }
            }
            for (String s :a) {
                get(s).q--;
            }
        } finally {
            l.unlock();
        }
    }
}