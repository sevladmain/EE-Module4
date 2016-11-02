package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.dao.MenuListDao;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by SeVlad on 31.10.2016.
 */
public class JdbcMenuListDao implements MenuListDao {
    private DataSource dataSource;
    private static final Logger LOGGER = Logger.getLogger(JdbcMenuListDao.class);

    public JdbcMenuListDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int addDishToMenu(int dishId, int menuId) {
        return 0;
    }

    @Override
    public int removeDishFromMenu(int dishId, int menuId) {
        return 0;
    }

    @Override
    public boolean isDishFromMenu(int dishId, int menuId) {
        return false;
    }

    @Override
    public List<Integer> getAllDishes(int menuId) {
        return null;
    }
}
