package ru.job4j.concurrent;
/*
 thread waiting
 */
public class Wget {

    public static void main(String[] args) {
        Thread loader = new Thread(() -> {
           for (int i = 0; i <= 100; i++) {
               System.out.print("\rLoading: " + i + "%");
               try {
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
        });
    loader.start();
    }
}
