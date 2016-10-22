package com.goit.homeworks.restaurant.core;

import java.util.Date;
import java.util.List;

/**
 * Created by SeVlad on 22.10.2016.
 */
public class Order {
    private int id;
    private Employee employee;
    private List<Dish> dishes;
    private int tableNum;
    private Date date;

    public Order(Employee employee, List<Dish> dishes, int tableNum, Date date) {
        this.employee = employee;
        this.dishes = dishes;
        this.tableNum = tableNum;
        this.date = date;
    }

    public Order(int id, Employee employee, List<Dish> dishes, int tableNum, Date date) {
        this.id = id;
        this.employee = employee;
        this.dishes = dishes;
        this.tableNum = tableNum;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public int getTableNum() {
        return tableNum;
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
