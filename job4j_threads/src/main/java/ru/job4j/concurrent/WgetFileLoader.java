package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class WgetFileLoader implements Runnable {

    private final String url;
    private final int speed;

    public WgetFileLoader(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(
                     "temp_" + Paths.get(new URI(url).getPath()).getFileName())) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int downloadData = 0;
            long startTime = System.currentTimeMillis();
            long startTimeBase = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                downloadData += bytesRead;
                if (downloadData >= speed) {
                    long spentTime = System.currentTimeMillis() - startTime;
                    if (spentTime < 1000) {
                        long timeToSleep = 1000 - spentTime;
                        Thread.sleep(timeToSleep);
                    }
                    downloadData = 0;
                    startTime = System.currentTimeMillis();
                }
            }
            Long endTime = System.currentTimeMillis();
            System.out.println("time: " + (endTime - startTimeBase));
        } catch (IOException | InterruptedException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Map<String, Object> paramNamesToValues = handleArgs(args);
        String url = (String) paramNamesToValues.get("url");
        int speed = (int) paramNamesToValues.get("speed");
        Thread wget = new Thread(
                new WgetFileLoader(url, speed));
        wget.start();
        wget.join();
    }

    private static Map<String, Object> handleArgs(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("parameters must be specified: args[0] is a link to the file,"
                    + " and args[1] is the speed of megabytes per second");
        }
        Map<String, Object> paramNamesToValues = new HashMap<>();
        String url = args[0];
        int speed = Integer.parseInt(args[1]) * 1048576;
        if (speed == 0) {
            throw new IllegalArgumentException("speed can't be zero");
        }
        paramNamesToValues.put("url", url);
        paramNamesToValues.put("speed", speed);
        return paramNamesToValues;
    }
}
