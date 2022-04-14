package ru.job4j.concurrent.threadpool.forkjoinpool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class ParallelBinarySearcher<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final int startIndex;
    private final int endIndex;
    private T elementToFind;

    public ParallelBinarySearcher(T[] array, int startIndex, int endIndex, T elementToFind) {
        this.array = array;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.elementToFind = elementToFind;
    }

    public void setElementToFind(T elementToFind) {
        this.elementToFind = elementToFind;
    }

    @Override
    protected Integer compute() {
        if (endIndex - startIndex <= 10) {
            return IntStream.range(startIndex, endIndex + 1).filter(i -> array[i].equals(elementToFind))
                                                            .findFirst()
                                                            .orElse(-1);
        }
        int middleIndex = (startIndex + endIndex) / 2;
        ParallelBinarySearcher<T> parallelSearchLeft = new ParallelBinarySearcher<>(array, startIndex,
                middleIndex - 1, elementToFind);
        ParallelBinarySearcher<T> parallelSearchRight = new ParallelBinarySearcher<>(array,
                middleIndex + 1, endIndex, elementToFind);
        parallelSearchLeft.fork();
        parallelSearchRight.fork();
        int leftResult = parallelSearchLeft.join();
        int rightResult = parallelSearchRight.join();
        return Math.max(leftResult, rightResult);
    }

    public Integer search() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool
                .invoke(new ParallelBinarySearcher<>(array, 0, array.length - 1, elementToFind));
    }
}
