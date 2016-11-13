package com.goit.homeworks.restaurant.services;

import com.goit.homeworks.restaurant.core.Employee;
import com.goit.homeworks.restaurant.core.Order;
import com.goit.homeworks.restaurant.core.PreparedDish;
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

    public Employee getEmployee(int id){
        return employeeDao.findEmployeeById(id);
    }
    public PreparedDish addPreparedDish(PreparedDish dish){
        return preparedDishDao.create(dish);
    }

    public int removePreparedDish(PreparedDish dish){
        return preparedDishDao.remove(dish);
    }

    public int setPreparedToDish(PreparedDish dish){
        dish.setPrepared(true);
        return preparedDishDao.update(dish);
    }

    public int closeOrder(Order order){
        order.setOpen(false);
        return orderDao.update(order);
    }
}
