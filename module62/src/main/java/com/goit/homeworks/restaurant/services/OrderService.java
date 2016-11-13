package com.goit.homeworks.restaurant.services;

import com.goit.homeworks.restaurant.core.Employee;
import com.goit.homeworks.restaurant.core.Order;
import com.goit.homeworks.restaurant.dao.*;

import java.util.List;

/**
 * Created by SeVlad on 13.11.2016.
 */
public class OrderService {
    OrderDao orderDao;
    EmployeeDao employeeDao;
    PreparedDishDao preparedDishDao;
    DishDao dishDao;

    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public void setPreparedDishDao(PreparedDishDao preparedDishDao) {
        this.preparedDishDao = preparedDishDao;
    }

    public void setDishDao(DishDao dishDao) {
        this.dishDao = dishDao;
    }

    public List<Order> getAllOpenOrders(){
        return orderDao.getAllOpenOrders();
    }
    public List<Order> getAllClosedOrders(){
        return orderDao.getAllClosedOrders();
    }

    public Order getOrderById(int id){
        return orderDao.findOrderById(id);
    }

    public int deleteOrder(Order order){
        return orderDao.remove(order);
    }

    public Order addOrder(Order order){
        return orderDao.create(order);
    }

    public int updateOrder(Order order){
        return orderDao.update(order);
    }

    public List<Employee> getAllEmployee(){
        return employeeDao.getAll();
    }

}
