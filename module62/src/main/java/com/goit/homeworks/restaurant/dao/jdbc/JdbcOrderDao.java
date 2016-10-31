package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.core.Order;
import com.goit.homeworks.restaurant.dao.DishDao;
import com.goit.homeworks.restaurant.dao.EmployeeDao;
import com.goit.homeworks.restaurant.dao.OrderDao;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by SeVlad on 30.10.2016.
 */
public class JdbcOrderDao implements OrderDao {
    private static final Logger LOGGER = Logger.getLogger(JdbcOrderDao.class);
    private DataSource dataSource;
    private DishDao dishDao;

    @Override
    public Order create(Order item) {
        return null;
    }

    @Override
    public int remove(Order item) {
        return 0;
    }

    @Override
    public int update(Order item) {
        return 0;
    }

    @Override
    public List<Order> getAll() {
        return null;
    }

    @Override
    public List<Order> getAllOpenOrders() {
        return null;
    }

    @Override
    public List<Order> getAllClosedOrders() {
        return null;
    }

    @Override
    public Order findOrderById(int id) {
        return null;
    }
}
