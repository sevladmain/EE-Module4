package com.goit.homeworks.restaurant.services;

import com.goit.homeworks.restaurant.dao.DishDao;
import com.goit.homeworks.restaurant.dao.MenuDao;
import com.goit.homeworks.restaurant.dao.MenuListDao;
import com.goit.homeworks.restaurant.model.Dish;
import com.goit.homeworks.restaurant.model.Menu;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public List<Menu> getAllMenus(){
        List<Menu> menus = new ArrayList<>();
        menus = menuDao.getAll();
        return menus;
    }

    @Transactional
    public List<Dish> getDishesFromMenu(int id){
        List<Dish> dishes = new ArrayList<>();
        List<Integer> dishesId = menuListDao.getAllDishes(id);
        dishes.addAll(dishesId
                .stream()
                .map(dish -> dishDao.findDishById(dish))
                .collect(Collectors.toList()));
        return dishes;
    }

    @Transactional
    public List<Menu> findMenuByName(String name){
        return menuDao.findMenuByName(name);
    }

    @Transactional
    public Menu getMenuById(int id){
        return menuDao.findMenuById(id);
    }

    @Transactional
    public boolean menuHasDishes(int id){
        return menuListDao.getAllDishes(id).size() > 0;
    }

    @Transactional
    public int deleteMenu(Menu menu){
        if(menuHasDishes(menu.getId())){
            return 0;
        } else{
            return menuDao.remove(menu);
        }
    }

    @Transactional
    public Menu addMenu(Menu menu){
        return menuDao.create(menu);
    }

    @Transactional
    public int updateMenu(Menu menu){
        return menuDao.update(menu);
    }

    @Transactional
    public int addDishToMenu(int dishId, int menuId){
        return menuListDao.addDishToMenu(dishId, menuId);
    }

    @Transactional
    public int removeDishFromMenu(int dishId, int menuId){
        return menuListDao.removeDishFromMenu(dishId, menuId);
    }

    @Transactional
    public List<Dish> getAllDishes(){
        return dishDao.getAll();
    }

    @Transactional
    public List<Dish> getNewDishes(int id) {
        List<Dish> newDishes = getAllDishes();
        List<Dish> exDishes = getDishesFromMenu(id);
        newDishes.removeAll(exDishes);
        return newDishes;
    }
}
