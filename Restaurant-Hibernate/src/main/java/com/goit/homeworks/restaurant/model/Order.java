package com.goit.homeworks.restaurant.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by SeVlad on 22.10.2016.
 */
@Entity
@Table(name = "ORDERS")
public class Order {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ID_EMP")
    private int employeeId;

    @Column(name = "TABLE_NUM")
    private int tableNum;

    @Column(name = "DATE")
    private Date date;

    @Column(name = "ISOPEN")
    private boolean open;

    public Order() {
        this(0, 0, 0, Date.valueOf("2010-01-01"), true);
    }

    public Order(int employeeId, int tableNum, Date date, boolean open) {
        this.employeeId = employeeId;
        this.tableNum = tableNum;
        this.date = date;
        this.open = open;
    }

    public Order(int id, int employeeId, int tableNum, Date date, boolean open) {
        this.id = id;
        this.employeeId = employeeId;
        this.tableNum = tableNum;
        this.date = date;
        this.open = open;
    }

    public boolean isNew(){ return id == 0; }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
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
        if (open != order.open) return false;
        return date != null ? date.equals(order.date) : order.date == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + employeeId;
        result = 31 * result + tableNum;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (open ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", employeeId=" + employeeId +
                ", tableNum=" + tableNum +
                ", date=" + date +
                ", open=" + open +
                '}';
    }
}
