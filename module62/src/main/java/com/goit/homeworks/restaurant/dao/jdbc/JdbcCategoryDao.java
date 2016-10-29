package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.core.Category;
import com.goit.homeworks.restaurant.dao.CategoryDao;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by SeVlad on 29.10.2016.
 */
public class JdbcCategoryDao implements CategoryDao {
    private DataSource dataSource;
    private static final Logger LOGGER = Logger.getLogger(JdbcPositionDao.class);

    public JdbcCategoryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public JdbcCategoryDao() {
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Category create(Category item) {
        return null;
    }

    @Override
    public int remove(Category item) {
        return -1;
    }

    @Override
    public int update(Category item) {
        return -1;
    }

    @Override
    public List<Category> getAll() {
        return null;
    }

    @Override
    public Category findCategoryById(int id) {
        return null;
    }
}
