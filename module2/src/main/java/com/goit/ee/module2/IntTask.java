package com.goit.ee.module2;

import java.util.Date;

/**
 * Created by SeVlad on 21.08.2016.
 */
public class IntTask implements Task<Integer> {
    private Integer result = 0;
    @Override
    public void execute() {
        result = -1;
    }

    @Override
    public Integer getResult() {
        return result;
    }
}
