package com.goit.homeworks.restaurant.dao;

import com.goit.homeworks.restaurant.core.Order;

import java.util.List;

/**
 * Created by SeVlad on 23.10.2016.
 */
public interface OrderDAO {
    boolean addOrder(Order order);
    boolean removeOrder(Order order);
    boolean updateOrder(Order order);
    List<Order> getAllOpenOrders();
    List<Order> getAllClosedOrders();
}
