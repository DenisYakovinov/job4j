package ru.job4j.concurrent.producerconsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParallelSearch {

    private static Logger logger
            = LoggerFactory.getLogger(ParallelSearch.class);

    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        final Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            Integer value = queue.poll();
                            if (logger.isInfoEnabled()) {
                                logger.info(String.valueOf(value));
                            }
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        try {
                            queue.offer(index);
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    consumer.interrupt();
                }
        ).start();
    }
}
