package com.goit.homeworks.restaurant.services;

import com.goit.homeworks.restaurant.dao.*;

/**
 * Created by SeVlad on 13.11.2016.
 */
public class OrderService {
    OrderDao orderDao;
    EmployeeDao employeeDao;
    OrderPreparedDishDao orderPreparedDishDao;
    PreparedDishDao preparedDishDao;
    DishDao dishDao;

    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public void setOrderPreparedDishDao(OrderPreparedDishDao orderPreparedDishDao) {
        this.orderPreparedDishDao = orderPreparedDishDao;
    }

    public void setPreparedDishDao(PreparedDishDao preparedDishDao) {
        this.preparedDishDao = preparedDishDao;
    }

    public void setDishDao(DishDao dishDao) {
        this.dishDao = dishDao;
    }

}
