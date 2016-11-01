package com.goit.homeworks.restaurant.core;

import java.sql.Date;

/**
 * Created by SeVlad on 22.10.2016.
 */
public class Order {
    private int id;
    private int employeeId;
    private int tableNum;
    private Date date;
    private boolean isOpen;

    public Order() {
    }

    public Order(int employeeId, int tableNum, Date date, boolean isOpen) {
        this.employeeId = employeeId;
        this.tableNum = tableNum;
        this.date = date;
        this.isOpen = isOpen;
    }

    public Order(int id, int employeeId, int tableNum, Date date, boolean isOpen) {
        this.id = id;
        this.employeeId = employeeId;
        this.tableNum = tableNum;
        this.date = date;
        this.isOpen = isOpen;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        if (employeeId != order.employeeId) return false;
        if (tableNum != order.tableNum) return false;
        if (isOpen != order.isOpen) return false;
        return date != null ? date.equals(order.date) : order.date == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + employeeId;
        result = 31 * result + tableNum;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (isOpen ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", employeeId=" + employeeId +
                ", tableNum=" + tableNum +
                ", date=" + date +
                ", isOpen=" + isOpen +
                '}';
    }
}
