package com.goit.homeworks;

/**
 * Created by SeVlad on 02.10.2016.
 */
public class EvalMultiplate implements Evaluator {
    @Override
    public String evaluate(String... args) {
        return String.valueOf((Integer.parseInt(args[1]) * Integer.parseInt(args[0])));
    }

    @Override
    public int getParametersNumber() {
        return 2;
    }
}
