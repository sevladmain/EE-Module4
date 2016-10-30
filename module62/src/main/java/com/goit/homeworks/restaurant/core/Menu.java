package com.goit.homeworks.restaurant.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SeVlad on 22.10.2016.
 */
public class Menu {
    private int id;
    private String name;
    private List<Dish> dishes;

    public Menu() {
        this(0, "", new ArrayList<>());
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public void addDish(Dish dish){
        dishes.add(dish);
    }

    public boolean removeDish(Dish dish){
        return dishes.remove(dish);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Menu menu = (Menu) o;

        if (id != menu.id) return false;
        if (name != null ? !name.equals(menu.name) : menu.name != null) return false;
        return dishes != null ? dishes.equals(menu.dishes) : menu.dishes == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (dishes != null ? dishes.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dishes=" + dishes +
                '}';
    }
}
