package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.core.PreparedDish;
import com.goit.homeworks.restaurant.dao.PreparedDishDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SeVlad on 30.10.2016.
 */
public class JdbcPreparedDishDao implements PreparedDishDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcPreparedDishDao.class);
    private DataSource dataSource;

    public JdbcPreparedDishDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public PreparedDish create(PreparedDish item) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO \"PREPARED_DISHES\" (\"ID_DISH\", \"ID_EMPLOYEE\", \"IS_PREPARED\", \"ID_ORDER\")  VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, item.getDishId());
            statement.setInt(2, item.getEmployeeId());
            statement.setBoolean(3, item.isPrepared());
            statement.setInt(4, item.getOrderId());
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
        int result = 0;
        if (item.getId() > 0) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement("DELETE FROM \"PREPARED_DISHES\" WHERE \"ID\"=?")) {
                statement.setInt(1, item.getId());
                result = statement.executeUpdate();
            } catch (SQLException e) {
                LOGGER.error("Exception while connecting to DB in method remove Prepared Dish: " + e);
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    @Override
    public int update(PreparedDish item) {
        int result = 0;
        if (item.getId() > 0) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement("UPDATE \"PREPARED_DISHES\" SET \"ID_DISH\"=?, \"ID_EMPLOYEE\"=?, \"IS_PREPARED\"=?, \"ID_ORDER\"=? WHERE ID=?")) {
                statement.setInt(1, item.getDishId());
                statement.setInt(2, item.getEmployeeId());
                statement.setBoolean(3, item.isPrepared());
                statement.setInt(4, item.getId());
                statement.setInt(5,item.getOrderId());
                result = statement.executeUpdate();
            } catch (SQLException e) {
                LOGGER.error("Exception while connecting to DB in method update PreparedStatement: " + item + e);
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    @Override
    public List<PreparedDish> getAll() {
        List<PreparedDish> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM \"PREPARED_DISHES\"");

            while (resultSet.next()) {
                PreparedDish dish = extractPreparedDish(resultSet);
                result.add(dish);
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method getAllPreparedDish: " + e);
            throw new RuntimeException(e);
        }
        return result;
    }

    private PreparedDish extractPreparedDish(ResultSet set) throws SQLException {
        PreparedDish dish = new PreparedDish();
        dish.setId(set.getInt("ID"));
        dish.setEmployeeId(set.getInt("ID_EMPLOYEE"));
        dish.setDishId(set.getInt("ID_DISH"));
        dish.setPrepared(set.getBoolean("IS_PREPARED"));
        dish.setOrderId(set.getInt("ID_ORDER"));
        return dish;
    }

    @Override
    public List<PreparedDish> findPreparedDishes() {
        List<PreparedDish> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM \"PREPARED_DISHES\" WHERE \"IS_PREPARED\"=TRUE");

            while (resultSet.next()) {
                PreparedDish dish = extractPreparedDish(resultSet);
                result.add(dish);
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method findPreparedDishes: " + e);
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public PreparedDish findPreparedDishById(int id) {
        PreparedDish dish = new PreparedDish();
        if (id > 0) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"PREPARED_DISHES\" WHERE \"ID\"=?")) {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()) {
                    dish = extractPreparedDish(resultSet);
                }
            } catch (SQLException e) {
                LOGGER.error("Exception while connecting to DB in method findPreparedDishById: " + e);
                throw new RuntimeException(e);
            }

        }
        return dish;
    }

    @Override
    public List<PreparedDish> getAllDishFromOrder(int orderId) {
        List<PreparedDish> result = new ArrayList<>();
        if(orderId > 0) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"PREPARED_DISHES\" WHERE \"ID_ORDER\"=?")) {
                statement.setInt(1, orderId);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    PreparedDish dish = extractPreparedDish(resultSet);
                    result.add(dish);
                }
            } catch (SQLException e) {
                LOGGER.error("Exception while connecting to DB in method getAllDishFromOrder: " + e);
                throw new RuntimeException(e);
            }
        }
        return result;
    }
    @Override
    public List<PreparedDish> getAllPreparedDishFromOrder(int orderId) {
        List<PreparedDish> result = new ArrayList<>();
        if(orderId > 0) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"PREPARED_DISHES\" WHERE \"ID_DISH\"=? AND \"IS_PREPARED\"=TRUE")) {
                statement.setInt(1, orderId);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    PreparedDish dish = extractPreparedDish(resultSet);
                    result.add(dish);
                }
            } catch (SQLException e) {
                LOGGER.error("Exception while connecting to DB in method getAllPreparedDishFromOrder: " + e);
                throw new RuntimeException(e);
            }
        }
        return result;
    }
}
