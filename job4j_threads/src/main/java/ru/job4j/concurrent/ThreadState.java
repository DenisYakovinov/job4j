package ru.job4j.concurrent;

import java.io.PrintStream;

public class ThreadState {

    private static PrintStream out = System.out;

    public static void main(String[] args) {
        Thread first = new Thread(
                () -> out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> out.println(Thread.currentThread().getName())
        );
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED || second.getState() != Thread.State.TERMINATED) {
            out.println("first thread state: " + first.getState());
            out.println("second thread state: " + second.getState());
        }
        out.println("all threads are finished");
    }
}
