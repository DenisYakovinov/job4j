package ru.job4j.concurrent.deadlock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLock {

    public static void main(String[] args) throws InterruptedException {
        Runner runner = new Runner();
        Thread thread1 = new Thread(runner::firstThread);
        Thread thread2 = new Thread(runner::secondThread);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        runner.finished();
    }
}

class Runner {

    private static Logger logger
            = LoggerFactory.getLogger(Runner.class);

    Random random = new Random();
    private final Account account1 = new Account();
    private final Account account2 = new Account();
    private final Lock lock1 = new ReentrantLock();
    private final Lock lock2 = new ReentrantLock();

    public void firstThread() {
        for (int i = 0; i < 10000; i++) {
            lock1.lock();
            /*while this thread is waiting for lock2 to unlock, thread2 is waiting for lock1 to unlock.*/
            lock2.lock();
            try {
                Account.transfer(account1, account2, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }

        /*
        for (int i = 0; i < 10000; i++) {
            synchronized (account1) {
                synchronized (account2) {
                    Account.transfer(account1, account2, random.nextInt(100));
                }
            }
        }
         */
    }

    public void secondThread() {
        for (int i = 0; i < 10000; i++) {
            lock2.lock();
            lock1.lock();
            try {
                Account.transfer(account2, account1, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }

        /*
        for (int i = 0; i < 10000; i++) {
            synchronized (account2) {
                synchronized (account1) {
                    Account.transfer(account2, account1, random.nextInt(100));
                }
            }
        }
         */
    }

    public void finished() {
        if (logger.isInfoEnabled()) {
            logger.info(String.valueOf(account1.getBalance()));
            logger.info(String.valueOf(account2.getBalance()));
            logger.info("Total balance {}", (account1.getBalance() + account2.getBalance()));
        }
    }
}

class Account {

    private int balance = 10000;

    public void deposit(int amount) {
        balance += amount;
    }

    public void withdraw(int amount) {
        balance -= amount;
    }

    public int getBalance() {
        return balance;
    }

    public static void transfer(Account acc1, Account acc2, int amount) {
        acc1.withdraw(amount);
        acc2.deposit(amount);
    }
}
