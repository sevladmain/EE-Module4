package com.goit.ee.module1;


import java.util.Date;

/**
 * Created by SeVlad on 14.08.2016.
 */
public class Trainer {
    private Runner runner;
    public Trainer(){
        runner = null;
    }

    public Trainer(Runner runner) {
        this.runner = runner;
    }

    public Runner getRunner() {
        return runner;
    }

    public void setRunner(Runner runner) {
        this.runner = runner;
    }
    public double calculateAverageTimeRun(String method, long numberOfIteration){
        long start = System.nanoTime();
        for(long i = 0; i < numberOfIteration; i++) {
            runner.run(method);
        }
        long end = System.nanoTime();
        return (double) (end - start) / numberOfIteration;
    }
}
