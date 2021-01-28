package G1.EX2;

import java.util.concurrent.locks.ReentrantLock;

class Bank {
    private static class Account {
        private int balance;
        Account(int balance) { this.balance = balance; }
        int balance() { return balance; }
        boolean deposit(int value) {
            balance += value;
            return true;
        }
    }

    // Our single account, for now
    private Account savings = new Account(0);
    private ReentrantLock l = new ReentrantLock();

    // Account balance
    public int balance() {
        try {
            l.lock();
            return savings.balance();
        } finally {
            l.unlock();
        }
    }

    // Deposit
    boolean deposit(int value) {
        try {
            l.lock();
            return savings.deposit(value);
        } finally {
            l.unlock();
        }
    }
}
