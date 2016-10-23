package com.goit.homeworks.restaurant.dao;

import com.goit.homeworks.restaurant.core.Menu;

import java.util.List;

/**
 * Created by SeVlad on 23.10.2016.
 */
public interface MenuDAO {
    boolean addMenu(Menu menu);
    boolean removeMenu(Menu menu);
    boolean changeMenu(Menu menu);
    boolean findMenuByName(String name);
    List<Menu> getAllMenus();
}
