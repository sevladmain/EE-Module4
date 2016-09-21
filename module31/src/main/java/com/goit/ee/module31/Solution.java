package com.goit.ee.module31;

/**
 * Created by SeVlad on 03.09.2016.
 */
public class Solution {
    public static void main(String[] args) {
        Semaphore semaphore = new SemaphoreImlp(5);
        for (int i = 0; i < 10; i++) {
            new Thread(new SampleThread(i+1, semaphore)).start();
        }
    }
}
