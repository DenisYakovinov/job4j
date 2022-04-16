package ru.job4j.concurrent.withoutsharedresource;

public class ThreadSafeLazySingleton {
    private ThreadSafeLazySingleton() {
    }

    public static ThreadSafeLazySingleton getInstance() {
        return Holder.INSTANCE;
    }

    private static final class Holder {
        private static final ThreadSafeLazySingleton INSTANCE = new ThreadSafeLazySingleton();
    }

    public static void main(String[] args) {
        ThreadSafeLazySingleton tracker = ThreadSafeLazySingleton.getInstance();
    }
}
