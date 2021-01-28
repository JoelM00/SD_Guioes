package G6;

import java.util.concurrent.locks.ReentrantLock;

public class Calculator {
    private int acum;
    private int cont;
    public ReentrantLock l = new ReentrantLock();

    public Calculator() {
        this.acum = 0;
        this.cont = 0;
    }

    public int sum(int x) {
        this.acum += x;
        cont++;
        return acum;
    }

    public int sub(int x) {
        this.acum -= x;
        cont++;
        return acum;
    }

    public int div(int x) {
        this.acum /= x;
        cont++;
        return acum;
    }

    public int mul(int x) {
        this.acum *= x;
        cont++;
        return acum;
    }

    public int getAcum() {
        return acum;
    }

    public int media() {
        return acum/cont;
    }

}
