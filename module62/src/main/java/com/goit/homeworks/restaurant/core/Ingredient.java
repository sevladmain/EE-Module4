package com.goit.homeworks.restaurant.core;

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

    public Ingredient() {
    }

    public void setId(int id) {
        this.id = id;
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
