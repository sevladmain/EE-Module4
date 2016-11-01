package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.core.Order;
import com.goit.homeworks.restaurant.dao.OrderDao;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SeVlad on 30.10.2016.
 */
public class JdbcOrderDao implements OrderDao {
    private static final Logger LOGGER = Logger.getLogger(JdbcOrderDao.class);
    private DataSource dataSource;

    public JdbcOrderDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Order create(Order item) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO ORDERS (ID_EMP, TABLE_NUM, DATE, ISOPEN)  VALUES (?,?,?,?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, item.getEmployeeId());
            statement.setInt(2, item.getTableNum());
            statement.setDate(3, item.getDate());
            statement.setBoolean(4, item.isOpen());
            statement.executeUpdate();
            ResultSet set = statement.getGeneratedKeys();
            if (set.next()) {
                item.setId(set.getInt("ID"));
            } else {
                LOGGER.error("Unknown Error in create Order: " + item);
                throw new RuntimeException("Unknown Error in create Order");
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method create Order: " + e);
            throw new RuntimeException(e);
        }
        return item;
    }

    @Override
    public int remove(Order item) {
        int result = 0;
        if (item.getId() > 0) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement("DELETE FROM ORDERS WHERE ID=?")) {
                statement.setInt(1, item.getId());
                result = statement.executeUpdate();
            } catch (SQLException e) {
                LOGGER.error("Exception while connecting to DB in method remove Order: " + item + " " + e);
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    @Override
    public int update(Order item) {
        int result = 0;
        if (item.getId() > 0) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement("UPDATE ORDERS SET ID_EMP=?, TABLE_NUM=?, DATE=?, ISOPEN=? WHERE ID=?")) {
                statement.setInt(1, item.getEmployeeId());
                statement.setInt(2, item.getTableNum());
                statement.setDate(3, item.getDate());
                statement.setBoolean(4, item.isOpen());
                statement.setInt(5, item.getId());
                result = statement.executeUpdate();
            } catch (SQLException e) {
                LOGGER.error("Exception while connecting to DB in method update Order: " + item + e);
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    @Override
    public List<Order> getAll() {
        List<Order> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ORDERS");
            while (resultSet.next()) {
                Order order = extractOrder(resultSet);
                result.add(order);
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method getAllOrders: " + e);
            throw new RuntimeException(e);
        }
        return result;

    }

    private Order extractOrder(ResultSet set) throws SQLException {
        Order  order = new Order();
        order.setId(set.getInt("ID"));
        order.setEmployeeId(set.getInt("ID_EMP"));
        order.setTableNum(set.getInt("TABLE_NUM"));
        order.setDate(set.getDate("DATE"));
        order.setOpen(set.getBoolean("ISOPEN"));
        return order;
    }

    @Override
    public List<Order> getAllOpenOrders() {
        List<Order> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ORDERS WHERE ISOPEN=TRUE");
            while (resultSet.next()) {
                Order order = extractOrder(resultSet);
                result.add(order);
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method getAllOrders: " + e);
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Order> getAllClosedOrders() {
        List<Order> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ORDERS WHERE ISOPEN=FALSE");
            while (resultSet.next()) {
                Order order = extractOrder(resultSet);
                result.add(order);
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method getAllOrders: " + e);
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Order findOrderById(int id) {
        Order order = new Order();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM ORDERS WHERE ID =?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                order = extractOrder(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method find order by id: " + id + e);
            throw new RuntimeException(e);
        }
        return order;
    }
}
