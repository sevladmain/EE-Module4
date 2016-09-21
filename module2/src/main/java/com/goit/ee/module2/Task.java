package com.goit.ee.module2;

/**
 * Created by SeVlad on 21.08.2016.
 */
public interface Task <T>{
    // Метода запускает таск на выполнение
    void execute();

    // Возвращает результат выполнения
    T getResult();
}
