package com.goit.homeworks;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.expression.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

/**
 * Created by SeVlad on 02.10.2016.
 */
public class CalculatorSpringTest {
    public static void main(String[] args) throws IOException, WrongArgumentNumberException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        Calculator calculator = (Calculator) context.getBean("calculator");
        List operations = (List) context.getBean("newoperations");
        for (Object operation :
                operations) {
            calculator.getFactory().registerOperation((Operation)operation);
        }
        System.out.println("Enter formula:");
        String formula = reader.readLine();
        System.out.println(formula + " = " + calculator.calculate(formula));
        reader.close();

    }
}
