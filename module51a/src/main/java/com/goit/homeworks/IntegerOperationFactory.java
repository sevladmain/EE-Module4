package com.goit.homeworks;

/**
 * Created by SeVlad on 02.10.2016.
 */
public class IntegerOperationFactory extends OperationFactory {
    public IntegerOperationFactory(){
        Type type;
        Evaluator evalPlus, evalMinus;
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
        registerOperation(new Operation("+", type, 0, evalPlus));
        registerOperation(new Operation("-", type, 0, evalMinus));
    }
}
