package ru.job4j.concurrent.nonblockingoperations;

public class OptimisticException extends RuntimeException {

    public OptimisticException(String message) {
        super(message);
    }

    public OptimisticException(String message, Throwable cause) {
        super(message, cause);
    }
}

