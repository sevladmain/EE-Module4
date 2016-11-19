package com.goit.homeworks.restaurant.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by SeVlad on 22.10.2016.
 */
@Entity
@Table(name = "MENU")
public class Menu {
    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME")
    private String name;

    public Menu() {
        this(0, "");
    }

    public void setId(int id) {
        this.id = id;
    }

    public Menu(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Menu(String name) {
        this.name = name;
    }

    public boolean isNew(){ return id == 0; }

    public int getId() {
        return id;
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

        Menu menu = (Menu) o;

        if (id != menu.id) return false;
        return name != null ? name.equals(menu.name) : menu.name == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
