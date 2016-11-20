package com.goit.homeworks.restaurant.dao.hibernate;

import com.goit.homeworks.restaurant.dao.DishDao;
import com.goit.homeworks.restaurant.model.Dish;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Created by SeVlad on 20.11.2016.
 */
public class HDishDao implements DishDao {
    SessionFactory sessionFactory;

    public HDishDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Dish create(Dish item) {
        sessionFactory.getCurrentSession().save(item);
        return item;
    }

    @Override
    public int remove(Dish item) {
        sessionFactory.getCurrentSession().delete(item);
        return 1;
    }

    @Override
    public int update(Dish item) {
        sessionFactory.getCurrentSession().update(item);
        return 1;
    }

    @Override
    public List<Dish> getAll() {
        Query query = sessionFactory.getCurrentSession().createQuery("select d from Dish d");
        return query.list();
    }

    @Override
    public List<Dish> findDishByName(String name) {
        Query query = sessionFactory.getCurrentSession().createQuery("select d from Dish d where d.name like :name");
        query.setParameter("name", name);
        return query.list();
    }

    @Override
    public Dish findDishById(int id) {
        Query query = sessionFactory.getCurrentSession().createQuery("select d from Dish d where d.id=:id");
        query.setParameter("id", id);
        return (Dish) query.uniqueResult();
    }
}
