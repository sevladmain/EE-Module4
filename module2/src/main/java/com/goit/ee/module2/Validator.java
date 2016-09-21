package com.goit.ee.module2;

/**
 * Created by SeVlad on 21.08.2016.
 */
public interface Validator <T> {
    // Валидирует переданое значение
    boolean isValid(T result);
}
