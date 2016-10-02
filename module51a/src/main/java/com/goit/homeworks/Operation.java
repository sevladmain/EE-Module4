package com.goit.homeworks;

/**
 * Created by SSuchock on 27.09.16.
 */
public class Operation {
    private String name;
    private Type type;

    public Evaluator getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(Evaluator evaluator) {
        this.evaluator = evaluator;
    }

    private Evaluator evaluator;

    public Operation(String name, Type type, int priority, Evaluator evaluator) {
        this.name = name;
        this.type = type;
        this.priority = priority;
        this.evaluator = evaluator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Operation operation = (Operation) o;

        return name != null ? name.equals(operation.name) : operation.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int priority;
}
