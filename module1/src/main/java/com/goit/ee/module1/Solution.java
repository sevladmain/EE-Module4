package com.goit.ee.module1;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

/**
 * Created by SeVlad on 14.08.2016.
 */
public class Solution {
    public static int[] counts;
    public static int numberPerCount;
    public static String[] methods;
    public static String[] classes;
    public static double[][][] results;
    static{
        counts = new int[]{10_000, 100_000};
        numberPerCount = 100;
        methods = new String[] {"add", "get", "remove", "contains", "populate", "iterator.add", "iterator.remove"};
        classes = new String[] {"ArrayList", "LinkedList", "HashSet", "TreeSet"};
        results = new double[counts.length][classes.length][methods.length];

    }
    public static void main(String[] args) {
        Runner runner = null;
        Trainer trainer = new Trainer();
        List<Integer> list = null;
        Set <Integer> set = null;
        for (int i = 0; i < counts.length; i++) {
            for (int j = 0; j < classes.length; j++) {
                switch (classes[j]){
                    case "ArrayList" : list = new ArrayList<>();
                        runner = new ListRunner(list, counts[i]);
                        break;
                    case "LinkedList" : list = new LinkedList<>();
                        runner = new ListRunner(list, counts[i]);
                        break;
                    case "HashSet" : set = new HashSet<>();
                        runner = new SetRunner(set, counts[i]);
                    case "TreeSet" : set = new TreeSet<>();
                        runner = new SetRunner(set, counts[i]);
                }
                runner.run("populate");
                trainer.setRunner(runner);
                for (int k = 0; k < methods.length; k++) {
                    results[i][j][k] = trainer.calculateAverageTimeRun(methods[k], numberPerCount);
                }
            }

        }
        printResult();
        saveResult();
    }
    public static void printResult(){
        String format = "|%1$-12s|%2$-8s|%3$-8s|%4$-8s|%5$-10s|%6$-12s|%7$-14s|%8$-16s|\n";
        for (int i = 0; i < counts.length; i++) {
            System.out.println("Test for " + counts[i] + " collection volume (nanoTime):");
            String [] output = new String[methods.length + 1];
            output[0] = "Class";
            for (int j = 0; j < methods.length; j++) {
                output[j + 1] = methods[j];
            }
            System.out.format(String.format(format, output));
            for (int j = 0; j < classes.length; j++) {
                output[0] = classes[j];
                for (int k = 0; k < methods.length; k++) {
                    output[k + 1] = String.format("%1$,.0f", results[i][j][k]);
                }
                System.out.format(String.format(format, output));
            }
        }

    }
    public static void saveResult(){
        PrintStream out = System.out;
        try {
            PrintStream stream = new PrintStream("result.txt");
            System.setOut(stream);
            printResult();
            System.setOut(out);
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
