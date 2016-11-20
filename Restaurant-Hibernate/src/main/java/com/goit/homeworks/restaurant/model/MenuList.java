package com.goit.homeworks.restaurant.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by SeVlad on 20.11.2016.
 */
@Entity
@Table(name = "menulist")
public class MenuList {
    @Column(name = "id_menu")
    int menuId;

    @Column(name = "id_dish")
    int dishId;

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
