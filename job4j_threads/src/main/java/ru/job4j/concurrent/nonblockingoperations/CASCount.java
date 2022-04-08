package ru.job4j.concurrent.nonblockingoperations;

import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {

    private static Logger logger
            = LoggerFactory.getLogger(CASCount.class);

    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public static void main(String[] args) throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread thread1 = new Thread(casCount::incrementTenTimes);
        Thread thread2 = new Thread(casCount::incrementTenTimes);
        Thread thread3 = new Thread(casCount::incrementTenTimes);
        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();
        if (logger.isInfoEnabled()) {
            logger.info("Count = {}", casCount.get());
        }
    }

    private void incrementTenTimes() {
        for (int i = 0; i < 10; i++) {
            increment();
        }
    }

    public void increment() {
        int oldValue;
        do {
            oldValue = count.get();
        } while (!count.compareAndSet(oldValue, oldValue + 1));
    }

    public int get() {
        return count.get();
    }
}