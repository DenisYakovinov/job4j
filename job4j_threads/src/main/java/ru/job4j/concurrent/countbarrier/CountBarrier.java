package ru.job4j.concurrent.countbarrier;

public class CountBarrier {

    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            count++;
            System.out.println(String.format(" %s Count = %d ", Thread.currentThread().getName(), count));
            monitor.notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            while (count < total) {
                try {
                    System.out.println(Thread.currentThread().getName() + " - WAITED");
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println(Thread.currentThread().getName() + " - AWAKE");
        }
    }
}