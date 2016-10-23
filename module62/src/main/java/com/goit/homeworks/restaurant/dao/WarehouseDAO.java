package com.goit.homeworks.restaurant.dao;

import com.goit.homeworks.restaurant.core.Ingredient;

import java.util.List;

/**
 * Created by SeVlad on 23.10.2016.
 */
public interface WarehouseDAO {
    boolean addIngredient(Ingredient ingredient);
    boolean removeIngredient(Ingredient ingredient);
    boolean updateIngredientAmount(Ingredient ingredient);
    boolean findIngredientByName(String name);
    List<Ingredient> getAllIngredients();
    List<Ingredient> getAllEndIngredients(int minAmount);

}
