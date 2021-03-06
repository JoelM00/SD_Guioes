package G2.EX1_2;

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
        boolean withdraw(int value) {
            if (value > balance)
                return false;
            balance -= value;
            return true;
        }
    }

    // Bank slots and vector of accounts
    private final int slots;
    private Account[] av;
    private ReentrantLock l = new ReentrantLock();

    public Bank(int n) {
        slots = n;
        av = new Account[slots];
        for (int i = 0; i<slots; i++)
            av[i] = new Account(0);
    }

    // Account balance
    public int balance(int id) {
        if (id < 0 || id >= slots) return 0;
        try {
            l.lock();
            return av[id].balance();
        } finally {
            l.unlock();
        }
    }

    // Deposit
    boolean deposit(int id, int value) {
        if (id < 0 || id >= slots) return false;
        try {
            l.lock();
            return av[id].deposit(value);
        } finally {
            l.unlock();
        }
    }

    // Withdraw; fails if no such account or insufficient balance
    public boolean withdraw(int id, int value) {
        if (id < 0 || id >= slots) return false;
        try {
            l.lock();
            return av[id].withdraw(value);
        } finally {
            l.unlock();
        }
    }

    public boolean transfer(int from,int to,int value) {
        if (this.balance(from) < value) return false;
        else {
            this.withdraw(from, value);
            this.deposit(to, value);
            return true;
        }
    }

    public int totalBalance() {
        int acum = 0;
        try {
            l.lock();
            for (Account a : av) {
                acum += a.balance;
            }
            return acum;
        } finally {
            l.unlock();
        }
    }
}