package com.goit.homeworks.restaurant.model;

import javax.persistence.*;

/**
 * Created by SeVlad on 30.10.2016.
 */
@Entity
@Table(name = "PREPARED_DISHES")
public class PreparedDish {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_DISH")
    private Dish dish;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_EMPLOYEE")
    private Employee employee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_ORDER")
    private Order order;

    @Column(name = "IS_PREPARED")
    private boolean prepared;

    public PreparedDish() {
        this(new Dish(), new Employee(), new Order(), false);
    }

    public PreparedDish(Dish dish, Employee employee, Order order, boolean prepared) {
        this.dish = dish;
        this.employee = employee;
        this.order = order;
        this.prepared = prepared;
    }

    public PreparedDish(int id, Dish dish, Employee employee, Order order, boolean prepared) {

        this.id = id;
        this.dish = dish;
        this.employee = employee;
        this.order = order;
        this.prepared = prepared;
    }

    public boolean isNew(){ return id == 0; }

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
        return prepared;
    }

    public void setPrepared(boolean prepared) {
        this.prepared = prepared;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PreparedDish that = (PreparedDish) o;

        if (id != that.id) return false;
        if (prepared != that.prepared) return false;
        if (dish != null ? !dish.equals(that.dish) : that.dish != null) return false;
        if (employee != null ? !employee.equals(that.employee) : that.employee != null) return false;
        return order != null ? order.equals(that.order) : that.order == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (dish != null ? dish.hashCode() : 0);
        result = 31 * result + (employee != null ? employee.hashCode() : 0);
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (prepared ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PreparedDish{" +
                "id=" + id +
                ", dish=" + dish +
                ", employee=" + employee +
                ", order=" + order +
                ", prepared=" + prepared +
                '}';
    }
}
