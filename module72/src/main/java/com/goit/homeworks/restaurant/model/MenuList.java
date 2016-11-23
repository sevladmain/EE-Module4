package com.goit.homeworks.restaurant.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by SeVlad on 20.11.2016.
 */
@Entity
@IdClass(MenuKey.class)
@Table(name = "menulist")
public class MenuList implements Serializable{
    @Id
    @Column(name = "id_menu")
    int menuId;

    @Id
    @Column(name = "id_dish")
    int dishId;

    public MenuList() {
    }

    public MenuList(int menuId, int dishId) {
        this.menuId = menuId;
        this.dishId = dishId;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }
}

class MenuKey implements Serializable{
    int menuId;
    int dishId;

    public MenuKey() {
    }

    public MenuKey(int menuId, int dishId) {
        this.menuId = menuId;
        this.dishId = dishId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuKey menuKey = (MenuKey) o;

        if (menuId != menuKey.menuId) return false;
        return dishId == menuKey.dishId;

    }

    @Override
    public int hashCode() {
        int result = menuId;
        result = 31 * result + dishId;
        return result;
    }
}
