package com.goit.homeworks.restaurant.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by SeVlad on 20.11.2016.
 */
@Entity
@Table(name = "ingredientlist")
@IdClass(IngredientList.class)
public class IngredientList implements Serializable {
    @Id
    @Column(name = "id_ingredient")
    private int ingredientId;

    @Id
    @Column(name = "id_dish")
    private int dishId;

    @Column(name = "used_amount")
    private int amount;

    public IngredientList() {
    }

    public IngredientList(int ingredientId, int dishId, int amount) {
        this.ingredientId = ingredientId;
        this.dishId = dishId;
        this.amount = amount;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

class IngredientKey implements Serializable{
    public int ingredientId;
    public int dishId;

    public IngredientKey() {
    }

    public IngredientKey(int ingredientId, int dishId) {
        this.ingredientId = ingredientId;
        this.dishId = dishId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IngredientKey that = (IngredientKey) o;

        if (ingredientId != that.ingredientId) return false;
        return dishId == that.dishId;

    }

    @Override
    public int hashCode() {
        int result = ingredientId;
        result = 31 * result + dishId;
        return result;
    }
}