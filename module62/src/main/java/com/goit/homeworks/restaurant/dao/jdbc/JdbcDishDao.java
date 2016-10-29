package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.core.Dish;
import com.goit.homeworks.restaurant.dao.DishDao;

import java.util.List;

/**
 * Created by SeVlad on 29.10.2016.
 */
public class JdbcDishDao implements DishDao {
    @Override
    public Dish create(Dish item) {
        return null;
    }

    @Override
    public int remove(Dish item) {
        return 0;
    }

    @Override
    public int update(Dish item) {
        return 0;
    }

    @Override
    public List<Dish> getAll() {
        return null;
    }

    @Override
    public List<Dish> findDishByName(String name) {
        return null;
    }

    @Override
    public List<Dish> getAllPreparedDishes() {
        return null;
    }
}
