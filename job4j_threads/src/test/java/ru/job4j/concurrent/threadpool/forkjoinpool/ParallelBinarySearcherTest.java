package ru.job4j.concurrent.threadpool.forkjoinpool;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ParallelBinarySearcherTest {

    @Test
    void shouldReturnIndexOfElementWhenElementInArray() {
        Integer[] array = new Integer[] {1, 3, 2, 5, 5, 5, 13, 13, 9, 12, 89, -123};
        ParallelBinarySearcher<Integer> searcher = new ParallelBinarySearcher<>(array, 0, array.length, -123);
        assertEquals(11, searcher.search());
    }

    @Test
    void shouldReturnFirstIndexOfElementWhenSeveralSameElementsInArray() {
        Integer[] array = new Integer[] {1, 3, 2, 5, 5, 5, 13, 13, 9, 12, 89, -123};
        ParallelBinarySearcher<Integer> searcher = new ParallelBinarySearcher<>(array, 0, array.length, 13);
        assertEquals(6, searcher.search());
    }

    @Test
    void shouldReturnMinusOneWhenElementIsNotInArray() {
        Integer[] array = new Integer[] {1, 3, 2, 5, 5, 5, 13, 13, 9, 12, 89, -123};
        ParallelBinarySearcher<Integer> searcher = new ParallelBinarySearcher<>(array, 0, array.length, 155);
        assertEquals(-1, searcher.search());
    }

    @Test
    void shouldReturnMinusOneWhenStartIndexGreaterThanEndIndex() {
        Integer[] array = new Integer[] {1, 3, 2, 5, 5, 5, 13, 13, 9, 12, 89, -123};
        ParallelBinarySearcher<Integer> searcher = new ParallelBinarySearcher<>(array, 100, array.length, 155);
        assertEquals(-1, searcher.search());
    }
}
