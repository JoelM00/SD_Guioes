package G3.EX3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Bank {
    private static class Account {
        private int balance;
        private ReentrantLock l = new ReentrantLock();

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

    private Map<Integer, Account> map = new HashMap<>();
    private int nextId = 0;
    private ReentrantReadWriteLock l = new ReentrantReadWriteLock();
    private Lock wl = l.writeLock();
    private Lock rl = l.readLock();

    // create account and return account id
    public int createAccount(int balance) {
        Account c = new Account(balance);
        try {
            wl.lock();
            int id = nextId;
            nextId += 1;
            map.put(id, c);
            return id;
        } finally {
            wl.unlock();
        }
    }

    // close account and return balance, or 0 if no such account
    public int closeAccount(int id) {
        Account c;
        try {
            wl.lock();
            c = map.remove(id);
            if (c == null) return 0;
            c.l.lock();
        } finally {
            wl.unlock();
        }
        try {
            return c.balance();
        } finally {
            c.l.unlock();
        }
    }

    // account balance; 0 if no such account
    public int balance(int id) {
        Account c;
        try {
            rl.lock();
            c = map.get(id);
            if (c == null) return 0;
            c.l.lock();
        } finally {
            rl.unlock();
        }
        try {
            c.l.lock();
            return c.balance();
        } finally {
            c.l.unlock();
        }
    }

    // deposit; fails if no such account
    public boolean deposit(int id, int value) {
        Account c;
        try {
            rl.lock();
            c = map.get(id);
            if (c == null) return false;
            c.l.lock();
        } finally {
            rl.unlock();
        }
        try {
            return c.deposit(value);
        } finally {
            c.l.unlock();
        }
    }

    // withdraw; fails if no such account or insufficient balance
    public boolean withdraw(int id, int value) {
        Account c;
        try {
            rl.lock();
            c = map.get(id);
            if (c == null) return false;
            c.l.lock();
        } finally {
            rl.unlock();
        }
        try {
            return c.withdraw(value);
        } finally {
            c.l.unlock();
        }
    }

    // transfer value between accounts;
    // fails if either account does not exist or insufficient balance
    public boolean transfer(int from, int to, int value) {
        Account cfrom, cto;
        try {
            rl.lock();
            cfrom = map.get(from);
            cto = map.get(to);
            if (cfrom == null || cto ==  null) return false;
            cfrom.l.lock();
            cto.l.lock();
        } finally {
            rl.unlock();
        }
        try {
            return cfrom.withdraw(value) && cto.deposit(value);
        } finally {
            cfrom.l.unlock();
            cto.l.unlock();
        }
    }

    // sum of balances in set of accounts; 0 if some does not exist
    public int totalBalance(int[] ids) {
        int total = 0;
        List<Account> contas = new ArrayList<>();
        try {
            rl.lock();
            for (int i : ids) {
                Account c = map.get(i);
                if (c == null) return 0;
                c.l.lock();
                contas.add(c);
            }
        } finally {
            rl.unlock();
        }
        for (Account c : contas) {
            total += c.balance();
            c.l.unlock();
        }
        return total;
    }
}