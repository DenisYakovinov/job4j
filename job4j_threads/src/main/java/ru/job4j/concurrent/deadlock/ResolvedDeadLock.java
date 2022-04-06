package ru.job4j.concurrent.deadlock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ResolvedDeadLock {

    public static void main(String[] args) throws InterruptedException {
        RunnerResolved runner = new RunnerResolved();
        Thread thread1 = new Thread(runner::firstThread);
        Thread thread2 = new Thread(runner::secondThread);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        runner.finished();
    }
}

class RunnerResolved {

    private static Logger logger
            = LoggerFactory.getLogger(RunnerResolved.class);

    Random random = new Random();
    private final AccountResolved account1 = new AccountResolved();
    private final AccountResolved account2 = new AccountResolved();
    private final Lock lock1 = new ReentrantLock();
    private final Lock lock2 = new ReentrantLock();

  private void takeLocks(Lock lock1, Lock lock2) {
      boolean firstLockTaken = false;
      boolean secondLockTaken = false;
      while (true) {
          try {
              firstLockTaken = lock1.tryLock();
              secondLockTaken = lock2.tryLock();
          } finally {
              if (firstLockTaken && secondLockTaken) {
                  return;
              }
              if (firstLockTaken) {
                  lock1.unlock();
              }
              if (secondLockTaken) {
                  lock2.unlock();
              }
          }
          try {
              Thread.sleep(5);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
      }
  }

    public void firstThread() {
        for (int i = 0; i < 10000; i++) {
            takeLocks(lock1, lock2);
            try {
                AccountResolved.transfer(account1, account2, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public void secondThread() {
        for (int i = 0; i < 10000; i++) {
            takeLocks(lock2, lock1);
            try {
                AccountResolved.transfer(account2, account1, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public void finished() {
        if (logger.isInfoEnabled()) {
            logger.info(String.valueOf(account1.getBalance()));
            logger.info(String.valueOf(account2.getBalance()));
            logger.info("Total balance {}", (account1.getBalance() + account2.getBalance()));
        }
    }
}

class AccountResolved {

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

    public static void transfer(AccountResolved acc1, AccountResolved acc2, int amount) {
        acc1.withdraw(amount);
        acc2.deposit(amount);
    }
}
