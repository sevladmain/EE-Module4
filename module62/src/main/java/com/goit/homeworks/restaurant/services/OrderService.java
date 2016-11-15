package com.goit.homeworks.restaurant.services;

import com.goit.homeworks.restaurant.core.Dish;
import com.goit.homeworks.restaurant.core.Employee;
import com.goit.homeworks.restaurant.core.Order;
import com.goit.homeworks.restaurant.core.PreparedDish;
import com.goit.homeworks.restaurant.dao.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<Order, Employee> getAllOpenOrders(){
        Map<Order, Employee> result = new HashMap<>();
        List<Order> orders = orderDao.getAllOpenOrders();
        for (Order order :
                orders) {
            result.put(order, employeeDao.findEmployeeById(order.getEmployeeId()));
        }
        return result;
    }
    public Map<Order, Employee> getAllClosedOrders(){
        Map<Order, Employee> result = new HashMap<>();
        List<Order> orders = orderDao.getAllClosedOrders();
        for (Order order :
                orders) {
            result.put(order, employeeDao.findEmployeeById(order.getEmployeeId()));
        }
        return result;
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

    public int updatePreparedDish(PreparedDish dish){
        return preparedDishDao.update(dish);
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

    public Map<PreparedDish, Dish> getDishesFromOrder(int orderId){
        Map<PreparedDish, Dish> dishMap = new HashMap<>();
        List<PreparedDish> preparedDishes = preparedDishDao.getAllDishFromOrder(orderId);
        for (PreparedDish dish :
                preparedDishes) {
            dishMap.put(dish, dishDao.findDishById(dish.getDishId()));
        }
        return dishMap;
    }

    public List<Dish> getAllDishes(){
        return dishDao.getAll();
    }

    public PreparedDish getPreparedDishById(int id){
        return preparedDishDao.findPreparedDishById(id);
    }

    public Dish getDishById(int id){
        return dishDao.findDishById(id);
    }
}
