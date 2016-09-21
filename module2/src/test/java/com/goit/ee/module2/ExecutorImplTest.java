package com.goit.ee.module2;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by SeVlad on 25.08.2016.
 */
public class ExecutorImplTest {

    @Test
    public void getValidResults() throws Exception {
        ExecutorImpl executor = new ExecutorImpl();
        Long[] validResults = new Long[]{1l};
        executor.addTask(new IntTask());
        executor.addTask(new IntTask());
        executor.addTask(new LongTask(), new NumberValidator());
        executor.execute();
        assertArrayEquals(validResults, executor.getValidResults().toArray());

    }

    @Test
    public void getInvalidResults() throws Exception {
        ExecutorImpl executor = new ExecutorImpl();
        Integer[] invalidResults = new Integer[]{-1, -1};
        executor.addTask(new IntTask());
        executor.addTask(new IntTask());
        executor.addTask(new LongTask(), new NumberValidator());
        executor.execute();
        assertArrayEquals(invalidResults, executor.getInvalidResults().toArray());

    }

}