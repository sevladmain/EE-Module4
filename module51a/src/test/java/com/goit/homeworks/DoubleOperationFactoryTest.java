package com.goit.homeworks;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by SeVlad on 02.10.2016.
 */
public class DoubleOperationFactoryTest {
    private Calculator calculator;
    @BeforeSuite
    private void init(){
        DoubleOperationFactory factory = new DoubleOperationFactory();
        calculator = new Calculator(factory, factory.getOperation("+").getType());


    }

    @Test
    public void testCalculate() throws Exception {
        String formula = "1.2e+1+2.3";
        String result = "14.3";
        assertEquals(calculator.calculate(formula), result);

    }
}