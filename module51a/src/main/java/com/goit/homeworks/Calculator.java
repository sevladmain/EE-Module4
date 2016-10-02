package com.goit.homeworks;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by SSuchock on 27.09.16.
 */
public class Calculator {
    private OperationFactory factory;
    private List<String> arguments = new ArrayList<>();

    public OperationFactory getFactory() {
        return factory;
    }

    private Type type;

    public Calculator(OperationFactory factory, Type type) {
        this.factory = factory;
        this.type = type;
    }

    public void setFactory(OperationFactory factory) {
        this.factory = factory;
    }

    public Type getType() {
        return type;
    }

    public int getPriority(String name) {
        Operation op = factory.getOperation(name);
        if (op == null)
            return -1;
        else
            return op.getPriority();
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String parseVariables(String expression) {
        if (type == null) {
            throw new IllegalStateException("Type not set!");
        }
        Pattern pattern = Pattern.compile(type.getTypePattern());
        Matcher matcher = pattern.matcher(expression);
        arguments.clear();
        int i = 0;
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, "%" + i);
            arguments.add(matcher.group(1));
            i++;
        }
        matcher.appendTail(buffer);
        if (i == 0) {
            System.err.println("Pattern not found!");
        }

        return buffer.toString();
    }

    public String calculate(String expression) throws WrongArgumentNumberException {
        expression = parseVariables(expression);
        LinkedList<String> listArgs = new LinkedList<>();
        LinkedList<String> listOpers = new LinkedList<>();
        String c = "";
        for (int i = 0; i < expression.length(); i++) {
            c = expression.substring(i, i + 1);
            if (c.equals("(") ) {
                listOpers.add(c);
            } else if (c.equals(")") || c.equals(",")) {
                    while(!listOpers.getLast().equals("(")){
                        evaluateList(listArgs, factory.getOperation(listOpers.removeLast()));
                    }
                if(c.equals(")")){
                    listOpers.removeLast();
                }
            } else if(c.equals("%")){ // it is variable
                c = "";
                i++;
                while(i < expression.length() && Character.isDigit(expression.charAt(i))){
                    c += expression.charAt(i++);
                }
                i--;
                listArgs.add(arguments.get(Integer.parseInt(c)));
            } else { // it is function
                Operation oper = null;
                while(i < expression.length() && (oper = factory.getOperation(c)) == null){
                    c += expression.charAt(++i);
                }
                if(oper == null){
                    throw new IllegalStateException("Wrong function");
                }
                Operation lastOps = listOpers.isEmpty() ? null : factory.getOperation(listOpers.getLast());
                while(oper != null && !listOpers.isEmpty() && lastOps != null && lastOps.getPriority() >= oper.getPriority()){
                    evaluateList(listArgs, factory.getOperation(listOpers.removeLast()));
                    lastOps = listOpers.isEmpty() ? null : factory.getOperation(listOpers.getLast());
                }
                listOpers.add(c);
            }
        }
        while(!listOpers.isEmpty()){
            evaluateList(listArgs, factory.getOperation(listOpers.removeLast()));
        }
        return listArgs.get(0);
    }

    private void evaluateList(LinkedList<String> list, Operation operation) throws WrongArgumentNumberException {
        int parametersNumber = operation.getEvaluator().getParametersNumber();
        if (parametersNumber > list.size()) {
            throw new WrongArgumentNumberException("Incorrect number of parameters in " + operation.getName());
        }
        String[] params = new String[parametersNumber];
        for (int i = 0; i < parametersNumber; i++) {
            params[i] = list.removeLast();
        }
        list.add(operation.getEvaluator().evaluate(params));
    }

}
