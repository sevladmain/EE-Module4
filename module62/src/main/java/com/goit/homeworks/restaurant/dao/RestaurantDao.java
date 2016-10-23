package com.goit.homeworks.restaurant.dao;

import com.goit.homeworks.restaurant.core.*;

import java.util.List;

/**
 * Created by SeVlad on 23.10.2016.
 */
public interface RestaurantDao {
    boolean add(Dish dishes);
    boolean add(Employee employee);
    boolean add(Menu menu);
    boolean add(Order order);
    boolean add(Ingredient ingredient);

    boolean remove(Dish dishes);
    boolean remove(Employee employee);
    boolean remove(Menu menu);
    boolean remove(Order order);
    boolean remove(Ingredient ingredient);

    boolean update(Dish dishes);
    boolean update(Employee employee);
    boolean update(Menu menu);
    boolean update(Order order);
    boolean update(Ingredient ingredient);

    List<Dish> findDishByName(String name);
    List<Employee> findEmployeeByName(String name);
    List<Menu> findMenuByName(String name);
    List<Ingredient> findIngredientByName(String name);

    List<Dish> getAllDishes();
    List<Dish> getAllPreparedDishes();
    List<Employee> getAllEmployees();
    List<Menu> getAllMenus();
    List<Order> getAllOpenOrders();
    List<Order> getAllClosedOrders();
    List<Ingredient> getAllIngredients();
    List<Ingredient> getAllEndIngredients(int minAmount);

}
