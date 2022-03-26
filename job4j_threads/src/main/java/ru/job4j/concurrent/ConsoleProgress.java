package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        progress.interrupt();
    }

    @Override
    public void run() {
        char[] chars = new char[] {'-','\\', '|', '/'};
        int counter = 0;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
            if (counter == 4) {
                counter = 0;
            }
            System.out.print("\rLoading... " + chars[counter]);
            counter++;
        }
    }
}
