package com.goit.homeworks.restaurant.dao;

import com.goit.homeworks.restaurant.core.Dish;

import java.util.List;

/**
 * Created by SeVlad on 23.10.2016.
 */
public interface DishDAO {
    boolean addDishes(List<Dish> dishes);
    boolean removeDish(Dish dish);
    boolean updateDish(Dish dish);
    List<Dish> findDishByName(String name);
    List<Dish> getAllDishes();
    List<Dish> getAllPreparedDishes();

}
