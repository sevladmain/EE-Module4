package com.goit.homeworks.restaurant.services;

import com.goit.homeworks.restaurant.dao.DishDao;
import com.goit.homeworks.restaurant.dao.MenuDao;
import com.goit.homeworks.restaurant.dao.MenuListDao;

/**
 * Created by SeVlad on 12.11.2016.
 */
public class MenuService {
    private MenuDao menuDao;
    private MenuListDao menuListDao;
    private DishDao dishDao;

    public void setMenuDao(MenuDao menuDao) {
        this.menuDao = menuDao;
    }

    public void setMenuListDao(MenuListDao menuListDao) {
        this.menuListDao = menuListDao;
    }

    public void setDishDao(DishDao dishDao) {
        this.dishDao = dishDao;
    }

}
