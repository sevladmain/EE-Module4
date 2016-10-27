package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.core.Position;
import com.goit.homeworks.restaurant.dao.PositionDao;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by SeVlad on 27.10.2016.
 */
public class JdbcPositionDao implements PositionDao {
    private DataSource dataSource;
    private static final Logger LOGGER = Logger.getLogger(JdbcPositionDao.class);

    public DataSource getDataSource() {
        return dataSource;
    }

    public JdbcPositionDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public JdbcPositionDao() {
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public Position create(Position item) {
        return null;
    }

    @Override
    public boolean remove(Position item) {
        return false;
    }

    @Override
    public int update(Position item) {
        return 0;
    }

    @Override
    public List<Position> getAll() {
        return null;
    }

    @Override
    public Position findPositionById(int id) {
        return null;
    }
}
