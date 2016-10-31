package com.goit.homeworks.restaurant.dao;

import com.goit.homeworks.restaurant.core.PreparedDish;

import java.util.List;

/**
 * Created by SeVlad on 30.10.2016.
 */
public interface PreparedDishDao extends SimpleDao<PreparedDish> {
    List<PreparedDish> findPreparedDishes();
    PreparedDish findPreparedDishById(int id);
}
