package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.dao.IngredientListDao;

import java.util.List;

/**
 * Created by SeVlad on 31.10.2016.
 */
public class JdbcIngredientListDao implements IngredientListDao {
    @Override
    public int addIngredientToDish(int ingredientId, int dishId) {
        return 0;
    }

    @Override
    public int removeIngredientFromDish(int ingredientId, int dishId) {
        return 0;
    }

    @Override
    public boolean isIngredientFromDish(int ingredientId, int dishId) {
        return false;
    }

    @Override
    public List<Integer> getAllIngredientsIds(int dishId) {
        return null;
    }
}
