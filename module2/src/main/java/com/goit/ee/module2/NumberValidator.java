package com.goit.ee.module2;

/**
 * Created by SeVlad on 21.08.2016.
 */
public class NumberValidator implements Validator<Number> {
    @Override
    public boolean isValid(Number result) {
        return result.longValue() > 0;
    }
}
