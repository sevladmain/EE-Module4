package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.core.Ingredient;
import com.goit.homeworks.restaurant.dao.IngredientDao;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by SeVlad on 29.10.2016.
 */
public class JdbcIngredientDao implements IngredientDao {
    private DataSource dataSource;
    private static final Logger LOGGER = Logger.getLogger(JdbcIngredientDao.class);

    public JdbcIngredientDao() {
    }

    public JdbcIngredientDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Ingredient create(Ingredient item) {
        return null;
    }

    @Override
    public int remove(Ingredient item) {
        return 0;
    }

    @Override
    public int update(Ingredient item) {
        return 0;
    }

    @Override
    public List<Ingredient> getAll() {
        return null;
    }

    @Override
    public List<Ingredient> findIngredientByName(String name) {
        return null;
    }

    @Override
    public List<Ingredient> getAllEndIngredients(int minAmount) {
        return null;
    }

    @Override
    public Ingredient findIngredientById(int id) {
        return null;
    }
}
