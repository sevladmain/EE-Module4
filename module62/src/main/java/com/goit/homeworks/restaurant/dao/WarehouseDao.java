package com.goit.homeworks.restaurant.dao;

import com.goit.homeworks.restaurant.core.Ingredient;

import java.util.List;

/**
 * Created by SeVlad on 24.10.2016.
 */
public interface WarehouseDao extends SimpleDao<Ingredient> {
    List<Ingredient> findIngredientByName(String name);
    List<Ingredient> getAllEndIngredients(int minAmount);
}
