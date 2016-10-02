package com.goit.homeworks;

/**
 * Created by SeVlad on 02.10.2016.
 */
public class FloatOperationFactory extends OperationFactory {
    public FloatOperationFactory() {
        Type type;
        Evaluator evalPlus, evalMinus;
        type = new Type("Float", "((\\d*\\.)?\\d+)");
        evalPlus = new Evaluator() {
            @Override
            public String evaluate(String... args) {
                return String.valueOf((Float.parseFloat(args[1]) + Float.parseFloat(args[0])));
            }

            @Override
            public int getParametersNumber() {
                return 2;
            }
        };
        evalMinus = new Evaluator() {
            @Override
            public String evaluate(String... args) {
                return String.valueOf((Float.parseFloat(args[1]) - Float.parseFloat(args[0])));
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
