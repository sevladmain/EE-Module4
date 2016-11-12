package com.goit.homeworks.restaurant.services;

import com.goit.homeworks.restaurant.core.Dish;
import com.goit.homeworks.restaurant.core.Menu;
import com.goit.homeworks.restaurant.dao.DishDao;
import com.goit.homeworks.restaurant.dao.MenuDao;
import com.goit.homeworks.restaurant.dao.MenuListDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SeVlad on 12.11.2016.
 */
public class MenuService {
    private MenuDao menuDao;
    private MenuListDao menuListDao;
    private DishDao dishDao;

    public void setMenuDao(MenuDao menuDao) {
        this.menuDao = menuDao;
    }

    public void setMenuListDao(MenuListDao menuListDao) {
        this.menuListDao = menuListDao;
    }

    public void setDishDao(DishDao dishDao) {
        this.dishDao = dishDao;
    }

    public List<Menu> getAllMenus(){
        List<Menu> menus = new ArrayList<>();
        menus = menuDao.getAll();
        return menus;
    }

    public Map<Menu, List<Dish>> getDishesFromMenu(int id){
        Map<Menu, List<Dish>> menuListMap = new HashMap<>();
        List<Dish> dishes = new ArrayList<>();
        List<Integer> dishesId = menuListDao.getAllDishes(id);
        for (Integer dish :
                dishesId) {
            dishes.add(dishDao.findDishById(dish));
        }
        menuListMap.put(menuDao.findMenuById(id), new ArrayList<>());
        return menuListMap;
    }

    public List<Menu> findMenuByName(String name){
        return menuDao.findMenuByName(name);
    }

    public Menu getMenuById(int id){
        return menuDao.findMenuById(id);
    }

    public boolean menuHasDishes(int id){
        return menuListDao.getAllDishes(id).size() == 0;
    }

    public int deleteMenu(Menu menu){
        if(menuHasDishes(menu.getId())){
            return 0;
        } else{
            return menuDao.remove(menu);
        }
    }

    public Menu addMenu(Menu menu){
        return menuDao.create(menu);
    }

    public int updateMenu(Menu menu){
        return menuDao.update(menu);
    }

    public int addDishToMenu(int dishId, int menuId){
        return menuListDao.addDishToMenu(dishId, menuId);
    }

    public int removeDishFromMenu(int dishId, int menuId){
        return menuListDao.removeDishFromMenu(dishId, menuId);
    }
    public List<Dish> getAllDishes(){
        return dishDao.getAll();
    }
}
