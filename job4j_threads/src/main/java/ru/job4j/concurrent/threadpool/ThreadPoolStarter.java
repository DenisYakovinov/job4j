package ru.job4j.concurrent.threadpool;

public class ThreadPoolStarter {
    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        for (int i = 0; i < 10; i++) {
            threadPool.work(() -> System.out.println(Thread.currentThread().getName() + " is proceeding inside run"));
        }
        threadPool.shutdown();
    }
}
