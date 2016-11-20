package com.goit.homeworks.restaurant.dao.hibernate;

import com.goit.homeworks.restaurant.dao.MenuListDao;
import com.goit.homeworks.restaurant.model.MenuList;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by SeVlad on 20.11.2016.
 */
public class HMenuListDao implements MenuListDao {
    SessionFactory sessionFactory;

    public HMenuListDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public int addDishToMenu(int dishId, int menuId) {
        MenuList menuList = new MenuList(menuId, dishId);
        sessionFactory.getCurrentSession().save(menuList);
        return 1;
    }

    @Override
    @Transactional
    public int removeDishFromMenu(int dishId, int menuId) {
        MenuList menuList = new MenuList(menuId, dishId);
        sessionFactory.getCurrentSession().remove(menuList);
        return 1;
    }

    @Override
    @Transactional
    public boolean isDishFromMenu(int dishId, int menuId) {
        MenuList menuList;
        Query query = sessionFactory.getCurrentSession().createQuery("select m from MenuList where dishId=:dishId and menuId=:menuId");
        query.setParameter("dishId", dishId);
        query.setParameter("menuId", menuId);
        menuList = (MenuList) query.uniqueResult();
        return  menuList != null;
    }

    @Override
    @Transactional
    public List<Integer> getAllDishes(int menuId) {
        Query query = sessionFactory.getCurrentSession().createQuery("select m.dishId from MenuList m where menuId=:menuId");
        query.setParameter("menuId", menuId);
        return query.list();
    }
}
