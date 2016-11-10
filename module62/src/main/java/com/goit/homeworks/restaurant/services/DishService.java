package com.goit.homeworks.restaurant.services;

import com.goit.homeworks.restaurant.core.Category;
import com.goit.homeworks.restaurant.core.Dish;
import com.goit.homeworks.restaurant.dao.CategoryDao;
import com.goit.homeworks.restaurant.dao.DishDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SeVlad on 10.11.2016.
 */
public class DishService {
    private DishDao dishDao;
    private CategoryDao categoryDao;

    public DishDao getDishDao() {
        return dishDao;
    }

    public void setDishDao(DishDao dishDao) {
        this.dishDao = dishDao;
    }

    public CategoryDao getCategoryDao() {
        return categoryDao;
    }

    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public Map<Dish, Category> getAllDishes() {
        Map<Dish, Category> result = new HashMap<>();
        List<Dish> dishes = dishDao.getAll();
        for (Dish dish :
                dishes) {
            result.put(dish, categoryDao.findCategoryById(dish.getCategoryId()));
        }
        return result;
    }

    public Map<Dish, Category> findDishByName(String name) {
        Map<Dish, Category> result = new HashMap<>();
        List<Dish> dishes = dishDao.findDishByName(name);
        for (Dish dish :
                dishes) {
            result.put(dish, categoryDao.findCategoryById(dish.getCategoryId()));
        }
        return result;
    }

    public Dish getDishById(int id) {
        return dishDao.findDishById(id);
    }

    public int deleteDish(Dish dish) {
        return dishDao.remove(dish);
    }

    public Dish addDish(Dish dish) {
        return dishDao.create(dish);
    }

    public List<Category> getAllCategories() {
        return categoryDao.getAll();
    }

    public int updateDish(Dish dish) {
        return dishDao.update(dish);
    }
}
