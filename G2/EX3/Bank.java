package G2.EX3;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

class Bank {
    private static class Account {
        private int balance;
        public ReentrantLock l = new ReentrantLock();

        Account(int balance) {
            this.balance = balance;
        }

        int balance() {
            return balance;
        }

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
            av[id].l.lock();
        } finally {
            l.unlock();
        }
        try {
            return av[id].balance();
        } finally {
            av[id].l.unlock();
        }
    }

    // Deposit
    boolean deposit(int id, int value) {
        if (id < 0 || id >= slots) return false;
        try {
            l.lock();
            av[id].l.lock();
        } finally {
            l.unlock();
        }
        try {
            return av[id].deposit(value);
        } finally {
            av[id].l.unlock();
        }
    }

    // Withdraw; fails if no such account or insufficient balance
    public boolean withdraw(int id, int value) {
        if (id < 0 || id >= slots) return false;
        try {
            l.lock();
            av[id].l.lock();
        } finally {
            l.unlock();
        }
        try {
            return av[id].withdraw(value);
        } finally {
            av[id].l.unlock();

        }
    }

    public boolean transfer(int from,int to,int value) {
        if (this.balance(from) < value) return false;
        else {
            try {
                l.lock();
                if (from < to) {
                    av[from].l.lock();
                    av[to].l.lock();
                } else {
                    av[to].l.lock();
                    av[from].l.lock();
                }
            } finally {
                l.unlock();
            }
            try {
                av[from].withdraw(value);
                av[to].deposit(value);
                return true;
            } finally {
                av[to].l.unlock();
                av[from].l.unlock();
            }
        }
    }

    public int totalBalance() {
        int acum = 0;
        try {
            l.lock();
            for (Account a : av) {
                a.l.lock();
                acum += a.balance;
                a.l.unlock();
            }
            return acum;
        } finally {
            l.unlock();
        }
    }
}