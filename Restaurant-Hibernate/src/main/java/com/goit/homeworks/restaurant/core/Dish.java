package com.goit.homeworks.restaurant.core;

/**
 * Created by SeVlad on 22.10.2016.
 */
public class Dish {
    private int id;
    private String name;
    private int categoryId;
    private int price;
    private int weight;

    public Dish() {
        this(0, "", 0, 0, 0);
    }

    public void setId(int id) {
        this.id = id;
    }

    public Dish(String name, int categoryId, int price, int weight) {
        this.name = name;
        this.categoryId = categoryId;
        this.price = price;
        this.weight = weight;
    }

    public Dish(int id, String name, int categoryId, int price, int weight) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.price = price;
        this.weight = weight;
    }
    public boolean isNew(){ return id == 0; }

    public int getId() {
        return id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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
        if (categoryId != dish.categoryId) return false;
        if (price != dish.price) return false;
        if (weight != dish.weight) return false;
        return name != null ? name.equals(dish.name) : dish.name == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + categoryId;
        result = 31 * result + price;
        result = 31 * result + weight;
        return result;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categoryId=" + categoryId +
                ", price=" + price +
                ", weight=" + weight +
                '}';
    }
}
