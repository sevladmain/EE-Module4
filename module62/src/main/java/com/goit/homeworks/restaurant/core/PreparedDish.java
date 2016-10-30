package com.goit.homeworks.restaurant.core;

/**
 * Created by SeVlad on 30.10.2016.
 */
public class PreparedDish {
    private int id;
    private Dish dish;
    private Employee employee;
    private boolean isPrepared;

    public PreparedDish() {
        this(0, new Dish(), new Employee(), false);
    }

    public PreparedDish(Dish dish, Employee employee, boolean isPrepared) {
        this.dish = dish;
        this.employee = employee;
        this.isPrepared = isPrepared;
    }

    public PreparedDish(int id, Dish dish, Employee employee, boolean isPrepared) {

        this.id = id;
        this.dish = dish;
        this.employee = employee;
        this.isPrepared = isPrepared;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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
        if (isPrepared != that.isPrepared) return false;
        if (dish != null ? !dish.equals(that.dish) : that.dish != null) return false;
        return employee != null ? employee.equals(that.employee) : that.employee == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (dish != null ? dish.hashCode() : 0);
        result = 31 * result + (employee != null ? employee.hashCode() : 0);
        result = 31 * result + (isPrepared ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PreparedDish{" +
                "id=" + id +
                ", dish=" + dish +
                ", employee=" + employee +
                ", isPrepared=" + isPrepared +
                '}';
    }
}
