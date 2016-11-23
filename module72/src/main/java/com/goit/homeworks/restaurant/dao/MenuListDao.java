package com.goit.homeworks.restaurant.dao;

import java.util.List;

/**
 * Created by SeVlad on 31.10.2016.
 */
public interface MenuListDao {
    int addDishToMenu(int dishId, int menuId);
    int removeDishFromMenu(int dishId, int menuId);
    boolean isDishFromMenu(int dishId, int menuId);
    List<Integer> getAllDishes(int menuId);
}
