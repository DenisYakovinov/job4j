package ru.job4j.concurrent.threadpool.emailnotification;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private static final String SUBJECT_PATTERN = "Notification %s to email %s";
    private static final String BODY_PATTERN = "Add a new event to %s";

    private final ExecutorService threadPool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    public void emailTo(User user) {
        String subject = String.format(SUBJECT_PATTERN, user.getName(), user.getEmail());
        String body = String.format(BODY_PATTERN, user.getName());
        threadPool.submit(() -> send(subject, body, user.getEmail()));
    }

    public void close() {
        threadPool.shutdown();
        while (!threadPool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String subject, String body, String email) {
    }
}
