package com.goit.homeworks.restaurant.core;

/**
 * Created by SeVlad on 30.10.2016.
 */
public class PreparedDish {
    private int id;
    private int dishId;
    private int employeeId;
    private boolean isPrepared;

    public PreparedDish() {
        this(0, 0, 0, false);
    }

    public PreparedDish(int dishId, int employeeId, boolean isPrepared) {
        this.dishId = dishId;
        this.employeeId = employeeId;
        this.isPrepared = isPrepared;
    }

    public PreparedDish(int id, int dishId, int employeeId, boolean isPrepared) {

        this.id = id;
        this.dishId = dishId;
        this.employeeId = employeeId;
        this.isPrepared = isPrepared;
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
        return isPrepared;
    }

    public void setPrepared(boolean prepared) {
        isPrepared = prepared;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PreparedDish that = (PreparedDish) o;

        if (id != that.id) return false;
        if (dishId != that.dishId) return false;
        if (employeeId != that.employeeId) return false;
        return isPrepared == that.isPrepared;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + dishId;
        result = 31 * result + employeeId;
        result = 31 * result + (isPrepared ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PreparedDish{" +
                "id=" + id +
                ", dishId=" + dishId +
                ", employeeId=" + employeeId +
                ", isPrepared=" + isPrepared +
                '}';
    }
}
