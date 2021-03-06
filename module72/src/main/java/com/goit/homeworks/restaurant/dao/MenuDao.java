package com.goit.homeworks.restaurant.dao;

import com.goit.homeworks.restaurant.model.Menu;

import java.util.List;

/**
 * Created by SeVlad on 24.10.2016.
 */
public interface MenuDao extends SimpleDao<Menu> {
    List<Menu> findMenuByName(String name);
    Menu findMenuById(int id);

}
