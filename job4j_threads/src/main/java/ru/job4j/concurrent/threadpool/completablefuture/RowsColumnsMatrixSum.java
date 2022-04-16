package ru.job4j.concurrent.threadpool.completablefuture;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RowsColumnsMatrixSum {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[][] matrix = {{2, 45, 11}, {1, 11, 11}, {1, 10, 19}};
        ColRowSum[] sums = sum(matrix);
        Arrays.stream(sums).sequential().forEach(System.out::print);
        ColRowSum[] sums1 = asyncSum(matrix);
        System.out.println();
        Arrays.stream(sums1).sequential().forEach(System.out::print);
    }

    public static ColRowSum[] sum(int[][] matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("The matrix can't be null");
        }
        ColRowSum[] sums = new ColRowSum[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int colSum = 0;
            int rowSum = 0;
            for (int j = 0; j < matrix.length; j++) {
                colSum += matrix[j][i];
                rowSum += matrix[i][j];
            }
            sums[i] = new ColRowSum(rowSum, colSum);
        }
        return sums;
    }

    public static ColRowSum[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        if (matrix == null) {
            throw new IllegalArgumentException("The matrix can't be null");
        }
        Map<Integer, CompletableFuture<ColRowSum>> futures = new HashMap<>();
        ColRowSum[] sums = new ColRowSum[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            futures.put(i, getTask(matrix, i));
        }
        for (Map.Entry<Integer, CompletableFuture<ColRowSum>> entry: futures.entrySet()) {
            int key = entry.getKey();
            sums[key] = futures.get(key).get();
        }
        return sums;
    }

    public static CompletableFuture<ColRowSum> getTask(int[][] matrix, int sumNumber) {
        return CompletableFuture.supplyAsync(() -> {
            int colSum = 0;
            int rowSum = 0;
            for (int k = 0; k < matrix.length; k++) {
                colSum += matrix[k][sumNumber];
                rowSum += matrix[sumNumber][k];
            }
            return new ColRowSum(rowSum, colSum);
        });
    }

    public static class ColRowSum {
        private int rowSum;
        private int colSum;

        public ColRowSum(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ColRowSum colRowSum = (ColRowSum) o;
            return rowSum == colRowSum.rowSum && colSum == colRowSum.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }

        @Override
        public String toString() {
            return "Sums{" + "rowSum=" + rowSum + ", colSum=" + colSum + '}';
        }
    }
}