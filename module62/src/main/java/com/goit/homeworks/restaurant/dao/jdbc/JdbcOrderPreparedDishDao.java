package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.dao.OrderPreparedDishDao;

import java.util.List;

/**
 * Created by SeVlad on 31.10.2016.
 */
public class JdbcOrderPreparedDishDao implements OrderPreparedDishDao {
    @Override
    public int addPreparedDishToOrder(int preparedDishId, int orderId) {
        return 0;
    }

    @Override
    public int removePreparedDishFromOrder(int preparedDishId, int orderId) {
        return 0;
    }

    @Override
    public boolean isPreparedDishFromOrder(int preparedDishId, int orderId) {
        return false;
    }

    @Override
    public List<Integer> getAllPreparedDish(int orderId) {
        return null;
    }
}
