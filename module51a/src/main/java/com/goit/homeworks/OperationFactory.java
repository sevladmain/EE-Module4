package com.goit.homeworks;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SSuchock on 27.09.16.
 */
public class OperationFactory {
    private List<Operation> operations = new ArrayList<>();

     public void registerOperation(Operation operation){
        if(operations.contains(operation)){
            throw new IllegalStateException("Operation already exists");
        }
        operations.add(operation);
    }
    public Operation getOperation(String name){
        for (Operation o :
                operations) {
            if (o.getName().equals(name)) {
                return o;
            }
        }
        return null;
    }
}
