package com.goit.ee.module31;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SeVlad on 01.09.2016.
 */
public class SemaphoreImlp implements Semaphore {
    private volatile int activePermits = 0;
    private int maxPermits;
    private final Object lock = new Object();

    public SemaphoreImlp() {
        this(1);
    }

    public SemaphoreImlp(int permits) {
        if (permits <= 0 )
            throw new IllegalArgumentException("Number of permits is negative: " + permits);
        maxPermits = permits;
    }

    @Override
    public void acquire() throws InterruptedException {
        synchronized (lock) {
            while (maxPermits == activePermits) {
                lock.wait();
            }
            activePermits++;
        }
    }

    @Override
    public void acquire(int permits) throws InterruptedException {

        if (permits <= 0 || permits > maxPermits)
            throw new IllegalArgumentException("Number of permits is incorrect: " + permits);
        for (int i = 0; i < permits; i++) {
            acquire();
        }
    }

    @Override
    public void release() {
        synchronized (lock) {
            if (activePermits > 0) {
                activePermits--;
                lock.notify();
            }
        }
    }

    @Override
    public void release(int permits) {
        if (permits <= 0 || permits > maxPermits)
            throw new IllegalArgumentException("Number of permits is incorrect: " + permits);
        for (int i = 0; i < permits; i++) {
           release();
        }
    }

    @Override
    public int getAvailablePermits() {
        return activePermits;
    }
}
