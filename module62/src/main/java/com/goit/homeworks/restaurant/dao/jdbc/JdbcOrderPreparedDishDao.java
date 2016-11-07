package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.dao.OrderPreparedDishDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SeVlad on 31.10.2016.
 */
public class JdbcOrderPreparedDishDao implements OrderPreparedDishDao {
    private DataSource dataSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcOrderPreparedDishDao.class);

    public JdbcOrderPreparedDishDao(DataSource dataSource) {

        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int addPreparedDishToOrder(int preparedDishId, int orderId) {
        int result = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO \"ORDER_PREPARED_DISHES\" (\"ID_ORDER\", \"ID_PREPARED_DISH\")  VALUES (?,?)")) {
            statement.setInt(1, orderId);
            statement.setInt(2, preparedDishId);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method addPreparedDishToOrder: " + e);
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int removePreparedDishFromOrder(int preparedDishId, int orderId) {
        int result = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM \"ORDER_PREPARED_DISHES\" WHERE \"ID_ORDER\"=? AND \"ID_PREPARED_DISH\"=?")) {
            statement.setInt(1, orderId);
            statement.setInt(2, preparedDishId);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method removePreparedDishFromOrder: " + e);
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean isPreparedDishFromOrder(int preparedDishId, int orderId) {
        boolean result = false;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"ORDER_PREPARED_DISHES\" WHERE \"ID_ORDER\"=? AND \"ID_PREPARED_DISH\"=?")) {
            statement.setInt(1, orderId);
            statement.setInt(2, preparedDishId);
            ResultSet set = statement.executeQuery();
            result = set.next();
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method isPreparedDishFromOrder: " + e);
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Integer> getAllPreparedDish(int orderId) {
        List<Integer> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"ORDER_PREPARED_DISHES\" WHERE \"ID_ORDER\"=?")) {
            statement.setInt(1, orderId);
            ResultSet set = statement.executeQuery();
            while (set.next()){
                result.add(set.getInt("ID_PREPARED_DISH"));
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method getAllPreparedDish: " + e);
            throw new RuntimeException(e);
        }
        return result;
    }
}
