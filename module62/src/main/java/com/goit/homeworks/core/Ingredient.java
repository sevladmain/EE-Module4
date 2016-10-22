package com.goit.homeworks.core;

/**
 * Created by SeVlad on 22.10.2016.
 */
public class Ingredient {
    private int id;
    private String name;

    public Ingredient(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Ingredient(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
