package com.goit.homeworks.restaurant.dao;

import java.util.List;

/**
 * Created by SeVlad on 31.10.2016.
 */
public interface OrderPreparedDishDao {
    int addPreparedDishToOrder(int preparedDishId, int orderId);
    int removePreparedDishFromOrder(int preparedDishId, int orderId);
    boolean isPreparedDishFromOrder(int preparedDishId, int orderId);
    List<Integer> getAllPreparedDish(int orderId);

}
