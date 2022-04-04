package ru.job4j.concurrent.producerconsumer;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    private final int capacity;

    public SimpleBlockingQueue(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("capacity must be greater then zero");
        }
        this.capacity = capacity;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() == capacity) {
            wait();
        }
        queue.offer(value);
        notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        T result = queue.poll();
        notifyAll();
        return result;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}