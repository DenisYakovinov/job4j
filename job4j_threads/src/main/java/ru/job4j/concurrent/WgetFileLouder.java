package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class WgetFileLouder implements Runnable {

    private final String url;
    private final int speed;

    public WgetFileLouder(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        if (speed == 0) {
            throw new IllegalArgumentException("speed can't be zero");
        }
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("file_tmp.xml")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long timeToSleep = Math.round(1000d / speed);
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                Thread.sleep(timeToSleep);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new WgetFileLouder(url, speed));
        wget.start();
        wget.join();
    }
}
