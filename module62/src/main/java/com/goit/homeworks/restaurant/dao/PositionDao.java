package com.goit.homeworks.restaurant.dao;

import com.goit.homeworks.restaurant.core.Position;

import java.util.List;

/**
 * Created by SeVlad on 26.10.2016.
 */
public interface PositionDao extends SimpleDao<Position> {
    Position findPositionById(int id);
}
