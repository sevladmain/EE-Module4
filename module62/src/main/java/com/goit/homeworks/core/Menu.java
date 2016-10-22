package com.goit.homeworks.core;

import java.util.List;

/**
 * Created by SeVlad on 22.10.2016.
 */
public class Menu {
    private int id;
    private String name;
    private List<Dish> dishes;

    public Menu(int id, String name, List<Dish> dishes) {
        this.id = id;
        this.name = name;
        this.dishes = dishes;
    }

    public Menu(String name, List<Dish> dishes) {
        this.name = name;
        this.dishes = dishes;
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

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }
}
