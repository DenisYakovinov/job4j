package ru.job4j.concurrent.threadpool.completablefuture;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RowsColumnsMatrixSumTest {

    @Test
    void shouldReturnSumsAsyncOfMatrix() throws ExecutionException, InterruptedException {
        int[][] matrix = {{2, 45, 11}, {1, 11, 11}, {1, 10, 19}};
        RowsColumnsMatrixSum.ColRowSum[] sums = {new RowsColumnsMatrixSum.ColRowSum(58, 4),
                new RowsColumnsMatrixSum.ColRowSum(23, 66),
                new RowsColumnsMatrixSum.ColRowSum(30, 41)};
        assertArrayEquals(sums, RowsColumnsMatrixSum.asyncSum(matrix));
    }

    @Test
    void shouldReturnSumsOfMatrixAsyncWhenAllElementsAreZero() throws ExecutionException, InterruptedException {
        int[][] matrixZero = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        RowsColumnsMatrixSum.ColRowSum[] sums = {new RowsColumnsMatrixSum.ColRowSum(0, 0),
                new RowsColumnsMatrixSum.ColRowSum(0, 0),
                new RowsColumnsMatrixSum.ColRowSum(0, 0)};
        assertArrayEquals(sums, RowsColumnsMatrixSum.asyncSum(matrixZero));
    }

    @Test
    void shouldReturnSumsNonAsyncOfMatrix() {
        int[][] matrix = {{2, 45, 11}, {1, 11, 11}, {1, 10, 19}};
        RowsColumnsMatrixSum.ColRowSum[] sums = {new RowsColumnsMatrixSum.ColRowSum(58, 4),
                new RowsColumnsMatrixSum.ColRowSum(23, 66),
                new RowsColumnsMatrixSum.ColRowSum(30, 41)};
        assertArrayEquals(sums, RowsColumnsMatrixSum.sum(matrix));
    }

    @Test
    void sumAsyncShouldThrowIllegalArgumentExceptionWhenMatrixIsNull() {
        assertThrows(IllegalArgumentException.class, () -> RowsColumnsMatrixSum.sum(null));
    }

    @Test
    void sumShouldThrowIllegalArgumentExceptionWhenMatrixIsNull() {
        assertThrows(IllegalArgumentException.class, () -> RowsColumnsMatrixSum.asyncSum(null));
    }
}
