package com.goit.homeworks.restaurant.core;

/**
 * Created by SeVlad on 30.10.2016.
 */
public class PreparedDish {
    private int id;
    private int dishId;
    private int employeeId;
    private int orderId;
    private boolean prepared;

    public PreparedDish() {
        this(0, 0, 0, false);
    }

    public PreparedDish(int dishId, int employeeId, int orderId, boolean prepared) {
        this.dishId = dishId;
        this.employeeId = employeeId;
        this.orderId = orderId;
        this.prepared = prepared;
    }

    public PreparedDish(int id, int dishId, int employeeId, int orderId, boolean prepared) {

        this.id = id;
        this.dishId = dishId;
        this.employeeId = employeeId;
        this.orderId = orderId;
        this.prepared = prepared;
    }

    public boolean isNew(){ return id == 0; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public boolean isPrepared() {
        return prepared;
    }

    public void setPrepared(boolean prepared) {
        this.prepared = prepared;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PreparedDish that = (PreparedDish) o;

        if (id != that.id) return false;
        if (dishId != that.dishId) return false;
        if (employeeId != that.employeeId) return false;
        if (orderId != that.orderId) return false;
        return prepared == that.prepared;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + dishId;
        result = 31 * result + employeeId;
        result = 31 * result + orderId;
        result = 31 * result + (prepared ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PreparedDish{" +
                "id=" + id +
                ", dishId=" + dishId +
                ", employeeId=" + employeeId +
                ", orderId=" + orderId +
                ", prepared=" + prepared +
                '}';
    }
}
