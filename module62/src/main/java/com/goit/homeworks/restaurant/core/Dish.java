package com.goit.homeworks.restaurant.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SeVlad on 22.10.2016.
 */
public class Dish {
    private int id;
    private Category category;
    private List<Ingredient> ingredientList;
    private int price;
    private int weight;
    private boolean isPrepared;
    private Employee whoPrepared;

    public Dish() {
        this(0, new Category(), new ArrayList<>(), 0, 0);
    }

    public void setId(int id) {
        this.id = id;
    }

    public Dish(Category category, List<Ingredient> ingredientList, int price, int weight) {
        this.category = category;
        this.ingredientList = ingredientList;
        this.price = price;
        this.weight = weight;
        this.isPrepared = false;
        this.whoPrepared = new Employee();
    }

    public Dish(int id, Category category, List<Ingredient> ingredientList, int price, int weight) {
        this.id = id;
        this.category = category;
        this.ingredientList = ingredientList;
        this.price = price;
        this.weight = weight;
        this.isPrepared = false;
        this.whoPrepared = new Employee();
    }

    public boolean isPrepared() {
        return isPrepared;
    }

    public void setPrepared(boolean prepared) {
        isPrepared = prepared;
    }

    public Employee getWhoPrepared() {
        return whoPrepared;
    }

    public void setWhoPrepared(Employee whoPrepared) {
        this.whoPrepared = whoPrepared;
    }

    public int getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dish dish = (Dish) o;

        if (id != dish.id) return false;
        if (!category.equals(dish.category)) return false;
        if (price != dish.price) return false;
        if (weight != dish.weight) return false;
        if (isPrepared != dish.isPrepared) return false;
        if (ingredientList != null ? !ingredientList.equals(dish.ingredientList) : dish.ingredientList != null)
            return false;
        return whoPrepared != null ? whoPrepared.equals(dish.whoPrepared) : dish.whoPrepared == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + category.hashCode();
        result = 31 * result + (ingredientList != null ? ingredientList.hashCode() : 0);
        result = 31 * result + price;
        result = 31 * result + weight;
        result = 31 * result + (isPrepared ? 1 : 0);
        result = 31 * result + (whoPrepared != null ? whoPrepared.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", category=" + category +
                ", ingredientList=" + ingredientList +
                ", price=" + price +
                ", weight=" + weight +
                ", isPrepared=" + isPrepared +
                ", whoPrepared=" + whoPrepared +
                '}';
    }
}
