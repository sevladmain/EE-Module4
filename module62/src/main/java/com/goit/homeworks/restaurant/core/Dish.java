package com.goit.homeworks.restaurant.core;

import java.util.List;

/**
 * Created by SeVlad on 22.10.2016.
 */
public class Dish {
    private int id;
    private int idCategory;
    private List<Ingredient> ingredientList;
    private int price;
    private int weight;
    private boolean isPrepared;
    private Employee whoPrepared;

    public Dish() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public Dish(int idCategory, List<Ingredient> ingredientList, int price, int weight) {
        this.idCategory = idCategory;
        this.ingredientList = ingredientList;
        this.price = price;
        this.weight = weight;
        this.isPrepared = false;
        this.whoPrepared = null;
    }

    public Dish(int id, int idCategory, List<Ingredient> ingredientList, int price, int weight) {
        this.id = id;
        this.idCategory = idCategory;
        this.ingredientList = ingredientList;
        this.price = price;
        this.weight = weight;
        this.isPrepared = false;
        this.whoPrepared = null;
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

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
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
}
