package com.goit.ee.module32;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by SeVlad on 06.09.2016.
 */
public class SquareSumImplTest {
    @Test(timeout = 5000)
    public void getSquareSum1() throws Exception {
        SquareSum s = new SquareSumImpl();
        int[] array = new int[] {1, 2, 3, 4, 5, 6};
        long result = 1*1 + 2*2 + 3*3 + 4*4 + 5*5 + 6*6;
        assertEquals(result, s.getSquareSum(array, 3));
    }
    @Test()
    public void getSquareSum2() throws Exception {
        SquareSum s = new SquareSumImpl();
        int[] array = new int[] {1, 2, 3, 4, 5, 6};
        long result = 1*1 + 2*2 + 3*3 + 4*4 + 5*5 + 6*6;
        assertEquals(result, s.getSquareSum(array, 6));
    }

    @Test()
    public void getSquareSum3() throws Exception {
        SquareSum s = new SquareSumImpl();
        int[] array = new int[1000];
        long result = 0l;
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
            result += i*i;
        }
        assertEquals(result, s.getSquareSum(array, 10));
    }

}