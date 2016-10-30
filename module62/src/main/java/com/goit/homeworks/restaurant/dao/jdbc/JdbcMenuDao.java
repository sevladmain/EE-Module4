package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.core.Menu;
import com.goit.homeworks.restaurant.dao.DishDao;
import com.goit.homeworks.restaurant.dao.MenuDao;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by SeVlad on 30.10.2016.
 */
public class JdbcMenuDao implements MenuDao {
    private static final Logger LOGGER = Logger.getLogger(JdbcMenuDao.class);
    private DishDao dishDao;

    public JdbcMenuDao(DishDao dishDao) {
        this.dishDao = dishDao;
    }

    public DishDao getDishDao() {
        return dishDao;
    }

    public void setDishDao(DishDao dishDao) {
        this.dishDao = dishDao;
    }

    @Override
    public Menu create(Menu item) {
        return null;
    }

    @Override
    public int remove(Menu item) {
        return 0;
    }

    @Override
    public int update(Menu item) {
        return 0;
    }

    @Override
    public List<Menu> getAll() {
        return null;
    }

    @Override
    public List<Menu> findMenuByName(String name) {
        return null;
    }

    @Override
    public Menu findMenuById(int id) {
        return null;
    }
}
