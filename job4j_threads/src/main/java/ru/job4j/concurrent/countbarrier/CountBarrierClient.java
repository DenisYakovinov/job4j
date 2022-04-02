package ru.job4j.concurrent.countbarrier;

public class CountBarrierClient {

    public static void main(String[] args) throws InterruptedException {
        CountBarrier countBarrier = new CountBarrier(5);
        Thread firstClient = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " first client thread started");
            for (int i = 0; i < 5; i++) {
                countBarrier.count();
            }
        }, "Thread-1");
        Thread secondClient = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " second client thread started");
            countBarrier.await();
            System.out.println(Thread.currentThread().getName() + " second client thread finished");
        }, "Thread-2");
        firstClient.start();
        secondClient.start();
        firstClient.join();
        secondClient.join();
    }
}
