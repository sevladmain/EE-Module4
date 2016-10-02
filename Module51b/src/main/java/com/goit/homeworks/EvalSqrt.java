package com.goit.homeworks;

/**
 * Created by SeVlad on 02.10.2016.
 */
public class EvalSqrt implements Evaluator {
    @Override
    public String evaluate(String... args) {
        return String.valueOf((int)Math.sqrt(Double.parseDouble(args[0])));
    }

    @Override
    public int getParametersNumber() {
        return 1;
    }
}
