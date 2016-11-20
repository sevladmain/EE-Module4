package com.goit.homeworks.restaurant.services;

import com.goit.homeworks.restaurant.model.*;
import com.goit.homeworks.restaurant.dao.*;
import org.springframework.transaction.annotation.Transactional;

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
    IngredientListDao ingredientListDao;
    IngredientDao ingredientDao;

    public void setIngredientListDao(IngredientListDao ingredientListDao) {
        this.ingredientListDao = ingredientListDao;
    }

    public void setIngredientDao(IngredientDao ingredientDao) {
        this.ingredientDao = ingredientDao;
    }

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

    @Transactional
    public Map<Order, Employee> getAllOpenOrders() {
        Map<Order, Employee> result = new HashMap<>();
        List<Order> orders = orderDao.getAllOpenOrders();
        for (Order order :
                orders) {
            result.put(order, employeeDao.findEmployeeById(order.getEmployeeId()));
        }
        return result;
    }

    @Transactional
    public Map<Order, Employee> getAllClosedOrders() {
        Map<Order, Employee> result = new HashMap<>();
        List<Order> orders = orderDao.getAllClosedOrders();
        for (Order order :
                orders) {
            result.put(order, employeeDao.findEmployeeById(order.getEmployeeId()));
        }
        return result;
    }

    @Transactional
    public Order getOrderById(int id) {
        return orderDao.findOrderById(id);
    }

    @Transactional
    public int deleteOrder(Order order) {
        return orderDao.remove(order);
    }

    @Transactional
    public Order addOrder(Order order) {
        return orderDao.create(order);
    }

    @Transactional
    public int updateOrder(Order order) {
        return orderDao.update(order);
    }

    @Transactional
    public List<Employee> getAllEmployee() {
        return employeeDao.getAll();
    }

    @Transactional
    public Employee getEmployee(int id) {
        return employeeDao.findEmployeeById(id);
    }

    @Transactional
    public PreparedDish addPreparedDish(PreparedDish dish) {
        return preparedDishDao.create(dish);
    }

    @Transactional
    public int updatePreparedDish(PreparedDish dish) {
        return preparedDishDao.update(dish);
    }

    @Transactional
    public int removePreparedDish(PreparedDish dish) {
        return preparedDishDao.remove(dish);
    }

    @Transactional
    public int setPreparedToDish(PreparedDish dish) {
        dish.setPrepared(true);
        return preparedDishDao.update(dish);
    }

    @Transactional
    public int closeOrder(Order order) {
        order.setOpen(false);
        return orderDao.update(order);
    }

    @Transactional
    public Map<PreparedDish, Dish> getDishesFromOrder(int orderId) {
        Map<PreparedDish, Dish> dishMap = new HashMap<>();
        List<PreparedDish> preparedDishes = preparedDishDao.getAllDishFromOrder(orderId);
        for (PreparedDish dish :
                preparedDishes) {
            dishMap.put(dish, dishDao.findDishById(dish.getDishId()));
        }
        return dishMap;
    }

    @Transactional
    public List<Dish> getAllDishes() {
        return dishDao.getAll();
    }

    @Transactional
    public PreparedDish getPreparedDishById(int id) {
        return preparedDishDao.findPreparedDishById(id);
    }

    @Transactional
    public Dish getDishById(int id) {
        return dishDao.findDishById(id);
    }

    @Transactional
    public int getUsedAmountOfDishIngredient(int ingredientId, int dishId) {
        return ingredientListDao.getUsedAmountOfDishIngredient(ingredientId, dishId);
    }

    @Transactional
    public void reduceIngredientsAmountFromPreparedDish(int preparedDishId) {
        PreparedDish preparedDish = preparedDishDao.findPreparedDishById(preparedDishId);
        List<Integer> ingredients = ingredientListDao.getAllIngredientsIds(preparedDish.getDishId());
        for (Integer ingredientId :
                ingredients) {
            int amount = getUsedAmountOfDishIngredient(ingredientId, preparedDish.getDishId());
            Ingredient ingredient = ingredientDao.findIngredientById(ingredientId);
            ingredient.setAmount(ingredient.getAmount() - amount);
            ingredientDao.update(ingredient);
        }
    }
}