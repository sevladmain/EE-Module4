package com.goit.ee.module32;

import java.util.concurrent.ExecutionException;

/**
 * Created by SeVlad on 06.09.2016.
 */
public interface SquareSum {
    long getSquareSum(int[] values, int numberOfThreads) throws ExecutionException, InterruptedException;
}
