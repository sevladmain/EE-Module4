package com.goit.homeworks.restaurant.dao.hibernate;

import com.goit.homeworks.restaurant.dao.IngredientListDao;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by SeVlad on 01.12.2016.
 */
public class HIngredientListDao implements IngredientListDao {
    SessionFactory sessionFactory;

    public HIngredientListDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public int addIngredientToDish(int ingredientId, int dishId, int amount) {
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

    @Override
    public int getUsedAmountOfDishIngredient(int ingredientId, int dishId) {
        return 0;
    }
}
