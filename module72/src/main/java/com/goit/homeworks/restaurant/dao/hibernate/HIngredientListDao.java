package com.goit.homeworks.restaurant.dao.hibernate;

import com.goit.homeworks.restaurant.dao.IngredientListDao;
import com.goit.homeworks.restaurant.model.IngredientList;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by SeVlad on 20.11.2016.
 */
public class HIngredientListDao implements IngredientListDao {
    SessionFactory sessionFactory;

    public HIngredientListDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public int addIngredientToDish(int ingredientId, int dishId, int amount) {
        IngredientList ingredientList = new IngredientList(ingredientId, dishId, amount);
        sessionFactory.getCurrentSession().save(ingredientList);
        return 1;
    }

    @Override
    @Transactional
    public int removeIngredientFromDish(int ingredientId, int dishId) {
        Query query = sessionFactory.getCurrentSession().createQuery("delete from IngredientList i where i.ingredientId=:ingredientId and i.dishId=:dishId");
        query.setParameter("ingredientId", ingredientId);
        query.setParameter("dishId", dishId);
        return query.executeUpdate();
    }

    @Override
    @Transactional
    public boolean isIngredientFromDish(int ingredientId, int dishId) {
        Query query = sessionFactory.getCurrentSession().createQuery("select i from IngredientList i where i.ingredientId=:ingredientId and i.dishId=:dishId");
        query.setParameter("ingredientId", ingredientId);
        query.setParameter("dishId", dishId);
        IngredientList ingredientList = (IngredientList) query.uniqueResult();
        return ingredientList != null;
    }

    @Override
    @Transactional
    public List<Integer> getAllIngredientsIds(int dishId) {
        Query query = sessionFactory.getCurrentSession().createQuery("select i.ingredientId from IngredientList i where i.dishId=:dishId");
        query.setParameter("dishId", dishId);

        return query.list();
    }

    @Override
    @Transactional
    public int getUsedAmountOfDishIngredient(int ingredientId, int dishId) {
        Query query = sessionFactory.getCurrentSession().createQuery("select i.amount from IngredientList i where i.ingredientId=:ingredientId and i.dishId=:dishId");
        query.setParameter("ingredientId", ingredientId);
        query.setParameter("dishId", dishId);
        int amount = (Integer) query.uniqueResult();
        return amount;
    }
}
