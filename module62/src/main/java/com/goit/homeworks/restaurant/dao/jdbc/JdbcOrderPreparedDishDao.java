package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.dao.OrderPreparedDishDao;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by SeVlad on 31.10.2016.
 */
public class JdbcOrderPreparedDishDao implements OrderPreparedDishDao {
    private DataSource dataSource;
    private static final Logger LOGGER = Logger.getLogger(JdbcOrderPreparedDishDao.class);

    public JdbcOrderPreparedDishDao(DataSource dataSource) {

        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int addPreparedDishToOrder(int preparedDishId, int orderId) {
        return 0;
    }

    @Override
    public int removePreparedDishFromOrder(int preparedDishId, int orderId) {
        return 0;
    }

    @Override
    public boolean isPreparedDishFromOrder(int preparedDishId, int orderId) {
        return false;
    }

    @Override
    public List<Integer> getAllPreparedDish(int orderId) {
        return null;
    }
}
