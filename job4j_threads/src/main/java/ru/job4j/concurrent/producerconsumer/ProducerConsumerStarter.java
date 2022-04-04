package ru.job4j.concurrent.producerconsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class ProducerConsumerStarter {

    private static Logger logger
            = LoggerFactory.getLogger(ProducerConsumerStarter.class);

    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        Random random = new Random();
        Thread consumer = new Thread(() -> {
            while (true) {
                try {
                    Integer value = queue.poll();
                    Thread.sleep(1000);
                    if (logger.isInfoEnabled()) {
                        logger.info(String.valueOf(value));
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        Thread producer = new Thread(() -> {
            while (true) {
                try {
                    queue.offer(random.nextInt(100));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        consumer.start();
        producer.start();
        consumer.join();
        producer.join();
    }
}
