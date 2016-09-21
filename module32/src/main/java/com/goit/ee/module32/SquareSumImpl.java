package com.goit.ee.module32;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by SeVlad on 06.09.2016.
 */
public class SquareSumImpl implements SquareSum {
    @Override
    public long getSquareSum(int[] values, int numberOfThreads) throws ExecutionException, InterruptedException {
        Phaser phaser = new Phaser(numberOfThreads);
        phaser.register();
        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
        List<Future<Long>> futureList = new ArrayList<>();
        if (numberOfThreads > values.length) numberOfThreads = values.length;
        int delta = values.length / numberOfThreads;
        for (int i = 0; i < numberOfThreads; i++) {
            Calculate calculate = new Calculate(phaser, i * delta, (i + 1) * delta, values);
            futureList.add(service.submit(calculate));
        }
        phaser.arriveAndAwaitAdvance();
        Long result = 0l;
        for (Future<Long> future :
                futureList) {
            result += future.get();
        }
        service.shutdown();
        return result;
    }

    public class Calculate implements Callable<Long> {
        private Phaser phaser;
        private int iBeg;
        private int iEnd;
        private int[] values;

        public Calculate(Phaser phaser, int iBeg, int iEnd, int[] values) {
            if (iEnd > values.length) iEnd = values.length;
            this.phaser = phaser;
            this.iBeg = iBeg;
            this.iEnd = iEnd;
            this.values = values;

        }

        @Override
        public Long call() throws Exception {
            phaser.arrive();
            Long result = 0l;
            for (int i = iBeg; i < iEnd; i++) {
                result += values[i] * values[i];
            }

            return result;
        }
    }
}
