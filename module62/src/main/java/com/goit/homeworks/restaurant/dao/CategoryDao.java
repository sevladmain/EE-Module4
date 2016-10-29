package com.goit.homeworks.restaurant.dao;

import com.goit.homeworks.restaurant.core.Category;

/**
 * Created by SeVlad on 29.10.2016.
 */
public interface CategoryDao extends SimpleDao<Category> {
    Category findCategoryById(int id);
}
