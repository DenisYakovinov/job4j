package ru.job4j.concurrent.threadpool.forkjoinpool;

public class ParallelSearchStarter {

    public static void main(String[] args) {
        Integer[] arr = new Integer[] {1, 3, 2, 5, 5, 5, 6, 13, 9, 12, 89, 123};
        ParallelBinarySearcher<Integer> searcher = new ParallelBinarySearcher<>(arr, 0, arr.length, 5);
        int indexOfElement = searcher.search();
        System.out.println(indexOfElement);
    }
}
