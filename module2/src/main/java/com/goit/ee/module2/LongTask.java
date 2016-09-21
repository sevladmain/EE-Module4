package com.goit.ee.module2;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by SeVlad on 21.08.2016.
 */
public class LongTask implements Task<Long> {
    private Long result = 0l;
    @Override
    public void execute() {
        result = 1l;
    }

    @Override
    public Long getResult() {
        return result;
    }
}
