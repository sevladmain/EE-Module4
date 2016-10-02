package com.goit.homeworks;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by SSuchock on 27.09.16.
 */
public class CalculatorTest {
    private Calculator calculator;
    private OperationFactory factory;
    private Evaluator evalPlus;
    private Evaluator evalMult;
    private Evaluator evalDiv;
    private Evaluator evalMinus;
    private Evaluator evalMax;
    private Evaluator evalSqrt;
    private Type type;

    @BeforeSuite
    public void init() {
        factory = new OperationFactory();
        type = new Type("Integer", "(\\d+)");
        evalPlus = new Evaluator() {
            @Override
            public String evaluate(String... args) {
                return String.valueOf((Integer.parseInt(args[1]) + Integer.parseInt(args[0])));
            }

            @Override
            public int getParametersNumber() {
                return 2;
            }
        };
        evalMinus = new Evaluator() {
            @Override
            public String evaluate(String... args) {
                return String.valueOf((Integer.parseInt(args[1]) - Integer.parseInt(args[0])));
            }

            @Override
            public int getParametersNumber() {
                return 2;
            }
        };
        evalMult = new Evaluator() {
            @Override
            public String evaluate(String... args) {
                return String.valueOf((Integer.parseInt(args[1]) * Integer.parseInt(args[0])));
            }

            @Override
            public int getParametersNumber() {
                return 2;
            }
        };
        evalDiv = new Evaluator() {
            @Override
            public String evaluate(String... args) {
                return String.valueOf((Integer.parseInt(args[1]) / Integer.parseInt(args[0])));
            }

            @Override
            public int getParametersNumber() {
                return 2;
            }
        };
        evalMax = new Evaluator() {
            @Override
            public String evaluate(String... args) {
                return String.valueOf((Integer.parseInt(args[1]) > Integer.parseInt(args[0]) ? Integer.parseInt(args[1]) : Integer.parseInt(args[0])));
            }

            @Override
            public int getParametersNumber() {
                return 2;
            }
        };
        evalSqrt = new Evaluator() {
            @Override
            public String evaluate(String... args) {
                return String.valueOf((int)Math.sqrt(Double.parseDouble(args[0])));
            }

            @Override
            public int getParametersNumber() {
                return 1;
            }
        };

        factory.registerOperation(new Operation("+", type, 0, evalPlus));
        factory.registerOperation(new Operation("-", type, 0, evalMinus));
        factory.registerOperation(new Operation("*", type, 1, evalMult));
        factory.registerOperation(new Operation("/", type, 1, evalDiv));
        factory.registerOperation(new Operation("max", type, 2, evalMax));
        factory.registerOperation(new Operation("sqrt", type, 2, evalSqrt));
        calculator = new Calculator(factory, type);

    }

    @Test
    public void testParse() throws Exception {
        String expression = "1+2-456+785*859-785";
        String result = "%0+%1-%2+%3*%4-%5";
        //System.out.println(calculator.parseVariables(expression));
        assertEquals(calculator.parseVariables(expression), result);
    }

    @Test
    public void FactoryTest() throws WrongArgumentNumberException{
        String expression = "(2+2)";
        String result = "4";
        expression = calculator.calculate(expression);
        assertEquals(expression, result);

    }

    @Test
    public void FactoryTest2() throws WrongArgumentNumberException{
        String expression = "2+2*2";
        String result = "6";
        expression = calculator.calculate(expression);
        assertEquals(expression, result);

    }

    @Test
    public void FactoryTest3() throws WrongArgumentNumberException{
        String expression = "1+2*2-(5+4*6)*(7-8*10+9)";
        String result = "1861";
        expression = calculator.calculate(expression);
        assertEquals(expression, result);

    }

    @Test
    public void FactoryTest4() throws WrongArgumentNumberException{
        String expression = "(5+4)*(7-8)";
        String result = "-9";
        expression = calculator.calculate(expression);
        assertEquals(expression, result);
    }

    @Test
    public void FactoryTest5() throws WrongArgumentNumberException{
        String expression = "1+1*1-(1+1*1)*(1-1+1)-1*1/1";
        String result = "-1";
        expression = calculator.calculate(expression);
        assertEquals(expression, result);
    }

    @Test
    public void FactoryTest6() throws WrongArgumentNumberException{
        String expression = "1+1*((1-((1+1*1)*(1-1+1)-1)*1))/1";
        String result = "1";
        expression = calculator.calculate(expression);
        assertEquals(expression, result);
    }

    @Test
    public void FactoryTest7() throws WrongArgumentNumberException{
        String expression = "1+max(1+2,(2-3)*4)";
        String result = "4";
        expression = calculator.calculate(expression);
        assertEquals(expression, result);
    }
   @Test
    public void FactoryTest8() throws WrongArgumentNumberException {
        String expression = "1+sqrt(max(max(1,2),(2-3)*sqrt(4)))";
        String result = "2";
        expression = calculator.calculate(expression);
        assertEquals(result, expression);
    }

    @Test(expectedExceptions = WrongArgumentNumberException.class)
    public void FactoryTest9() throws WrongArgumentNumberException {
        String expression = "max((2-3)*sqrt(4))";
        expression = calculator.calculate(expression);

    }
    @Test(expectedExceptions = Exception.class)
    public void FactoryTest10() throws WrongArgumentNumberException {
        String expression = "(2-3)*sqrt(4)))";
        expression = calculator.calculate(expression);
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void FactoryTest11() throws WrongArgumentNumberException {
        factory.registerOperation(new Operation("max", type, 2, evalDiv));
        calculator.setFactory(factory);
    }

}