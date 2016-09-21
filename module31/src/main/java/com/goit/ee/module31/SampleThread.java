package com.goit.ee.module31;

/**
 * Created by SeVlad on 03.09.2016.
 */
public class SampleThread implements Runnable {
    private Semaphore semaphore;
    private int id;

    public SampleThread(int id, Semaphore semaphore) {
        this.id = id;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        System.out.println("Thread " + id + "is waiting");
        try {
            semaphore.acquire();
            System.out.println("Thread " + id + "is running");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        semaphore.release();
        System.out.println("Thread " + id + "is quiting");
    }
}
