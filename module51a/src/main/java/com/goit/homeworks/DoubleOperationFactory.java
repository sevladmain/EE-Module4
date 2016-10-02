package com.goit.homeworks;

/**
 * Created by SeVlad on 02.10.2016.
 */
public class DoubleOperationFactory extends OperationFactory{
    public DoubleOperationFactory() {
        Type type;
        Evaluator evalPlus, evalMinus;
        type = new Type("Double", "((\\d*\\.)?\\d+([eE][-+]?\\d+)?)");
        evalPlus = new Evaluator() {
            @Override
            public String evaluate(String... args) {
                return String.valueOf((Double.parseDouble(args[1]) + Double.parseDouble(args[0])));
            }

            @Override
            public int getParametersNumber() {
                return 2;
            }
        };
        evalMinus = new Evaluator() {
            @Override
            public String evaluate(String... args) {
                return String.valueOf((Double.parseDouble(args[1]) - Double.parseDouble(args[0])));
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

