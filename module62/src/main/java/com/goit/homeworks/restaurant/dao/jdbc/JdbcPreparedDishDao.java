package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.core.PreparedDish;
import com.goit.homeworks.restaurant.dao.DishDao;
import com.goit.homeworks.restaurant.dao.EmployeeDao;
import com.goit.homeworks.restaurant.dao.PreparedDishDao;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

/**
 * Created by SeVlad on 30.10.2016.
 */
public class JdbcPreparedDishDao implements PreparedDishDao {
    private static final Logger LOGGER = Logger.getLogger(JdbcPreparedDishDao.class);
    private DataSource dataSource;
    private DishDao dishDao;
    private EmployeeDao employeeDao;

    public JdbcPreparedDishDao(DataSource dataSource, DishDao dishDao, EmployeeDao employeeDao) {
        this.dataSource = dataSource;
        this.dishDao = dishDao;
        this.employeeDao = employeeDao;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DishDao getDishDao() {
        return dishDao;
    }

    public void setDishDao(DishDao dishDao) {
        this.dishDao = dishDao;
    }

    public EmployeeDao getEmployeeDao() {
        return employeeDao;
    }

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public PreparedDish create(PreparedDish item) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO PREPARED_DISHES (ID_DISH, ID_EMPLOYEE, IS_PREPARED)  VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, item.getDish().getId());
            statement.setInt(2, item.getEmployee().getId());
            statement.setBoolean(3, item.isPrepared());
            statement.executeUpdate();
            ResultSet set = statement.getGeneratedKeys();
            if (set.next()) {
                item.setId(set.getInt("ID"));
            } else {
                LOGGER.error("Unknown Error in create PreparedDish" + item);
                throw new RuntimeException("Unknown Error in create PreparedDish");
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method create PreparedDish: " + e);
            throw new RuntimeException(e);
        }
        return item;
    }

    @Override
    public int remove(PreparedDish item) {
        return 0;
    }

    @Override
    public int update(PreparedDish item) {
        return 0;
    }

    @Override
    public List<PreparedDish> getAll() {
        return null;
    }

    @Override
    public List<PreparedDish> findPreparedDish() {
        return null;
    }

    @Override
    public PreparedDish findPreparedDishById(int id) {
        return null;
    }
}
