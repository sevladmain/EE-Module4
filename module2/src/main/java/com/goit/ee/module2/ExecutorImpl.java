package com.goit.ee.module2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SeVlad on 21.08.2016.
 */
public class ExecutorImpl <T> implements Executor<T>{
    private List<Task<? extends T>> tasks;
    private Validator<? super T> validator;
    private boolean isExecuted;
    private List<T> validResults;
    private List<T> invalidResults;

    public ExecutorImpl() {
        tasks = new ArrayList<>();
        validator = null;
        isExecuted = false;
        validResults = new ArrayList<>();
        invalidResults = new ArrayList<>();
    }


    @Override
    public void addTask(Task<? extends T> task) {
        if (isExecuted){
            throw new IllegalStateException("Tasks are already executed");
        }
        tasks.add(task);
    }

    @Override
    public void addTask(Task<? extends T> task, Validator<? super T> validator) {
        if (isExecuted){
            throw new IllegalStateException("Tasks are already executed");
        }
        tasks.add(task);
        this.validator = validator;
    }

    @Override
    public void execute() {
        if (validator == null){
            throw new IllegalStateException("Validator is not set");
        }
        for (Task<? extends T> task :
                tasks) {
            task.execute();
            T result = task.getResult();
            if (validator.isValid(result)) {
                validResults.add(result);
            } else {
                invalidResults.add(result);
            }
        }
        isExecuted = true;
    }

    @Override
    public List<T> getValidResults() {
        if(!isExecuted){
            throw new IllegalStateException("Tasks are not executed");
        }
        return validResults;
    }

    @Override
    public List<T> getInvalidResults() {
        if(!isExecuted){
            throw new IllegalStateException("Tasks are not executed");
        }
        return invalidResults;
    }
}
