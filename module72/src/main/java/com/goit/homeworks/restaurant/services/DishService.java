package com.goit.homeworks.restaurant.services;

import com.goit.homeworks.restaurant.dao.CategoryDao;
import com.goit.homeworks.restaurant.dao.DishDao;
import com.goit.homeworks.restaurant.model.Category;
import com.goit.homeworks.restaurant.model.Dish;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Map<Dish, Category> getAllDishes() {
        Map<Dish, Category> result = new HashMap<>();
        List<Dish> dishes = dishDao.getAll();
        for (Dish dish :
                dishes) {
            result.put(dish, categoryDao.findCategoryById(dish.getCategoryId()));
        }
        return result;
    }

    @Transactional
    public Map<Dish, Category> findDishByName(String name) {
        Map<Dish, Category> result = new HashMap<>();
        List<Dish> dishes = dishDao.findDishByName(name);
        for (Dish dish :
                dishes) {
            result.put(dish, categoryDao.findCategoryById(dish.getCategoryId()));
        }
        return result;
    }

    @Transactional
    public Dish getDishById(int id) {
        return dishDao.findDishById(id);
    }

    @Transactional
    public int deleteDish(Dish dish) {
        return dishDao.remove(dish);
    }

    @Transactional
    public Dish addDish(Dish dish) {
        return dishDao.create(dish);
    }

    @Transactional
    public List<Category> getAllCategories() {
        return categoryDao.getAll();
    }

    @Transactional
    public int updateDish(Dish dish) {
        return dishDao.update(dish);
    }
}
