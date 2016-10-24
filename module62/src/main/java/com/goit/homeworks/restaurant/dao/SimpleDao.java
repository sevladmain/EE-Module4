package com.goit.homeworks.restaurant.dao;

import java.util.List;

/**
 * Created by SeVlad on 24.10.2016.
 */
public interface SimpleDao <T> {
    T create(T item);
    T remove(T item);
    T update(T item);
    List<T> getAll();
}
