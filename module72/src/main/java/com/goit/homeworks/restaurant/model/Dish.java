package com.goit.homeworks.restaurant.model;

import javax.persistence.*;

/**
 * Created by SeVlad on 22.10.2016.
 */
@Entity
@Table(name = "DISHES")
public class Dish {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "NAME")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_CATEGORY")
    private Category category;

    @Column(name = "PRICE")
    private int price;

    @Column(name = "WEIGHT")
    private int weight;

    public Dish() {
        this(0, "", new Category(), 0, 0);
    }

    public void setId(int id) {
        this.id = id;
    }

    public Dish(String name, Category category, int price, int weight) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.weight = weight;
    }

    public Dish(int id, String name, Category category, int price, int weight) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.weight = weight;
    }
    public boolean isNew(){ return id == 0; }

    public int getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dish dish = (Dish) o;

        if (id != dish.id) return false;
        if (price != dish.price) return false;
        if (weight != dish.weight) return false;
        if (name != null ? !name.equals(dish.name) : dish.name != null) return false;
        return category != null ? category.equals(dish.category) : dish.category == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + price;
        result = 31 * result + weight;
        return result;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", price=" + price +
                ", weight=" + weight +
                '}';
    }
}
