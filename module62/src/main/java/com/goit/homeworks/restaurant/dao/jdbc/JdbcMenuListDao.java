package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.dao.MenuListDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SeVlad on 31.10.2016.
 */
public class JdbcMenuListDao implements MenuListDao {
    private DataSource dataSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcMenuListDao.class);

    public JdbcMenuListDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int addDishToMenu(int dishId, int menuId) {
        int result = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO \"MENULIST\" (\"ID_MENU\", \"ID_DISH\")  VALUES (?,?)")) {
            statement.setInt(1, menuId);
            statement.setInt(2, dishId);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method addDishToMenu: " + e);
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int removeDishFromMenu(int dishId, int menuId) {
        int result = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM \"MENULIST\" WHERE \"ID_MENU\"=? AND \"ID_DISH\"=?")) {
            statement.setInt(1, menuId);
            statement.setInt(2, dishId);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method removeDishFromMenu: " + e);
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean isDishFromMenu(int dishId, int menuId) {
        boolean result = false;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"MENULIST\" WHERE \"ID_MENU\"=? AND \"ID_DISH\"=?")) {
            statement.setInt(1, menuId);
            statement.setInt(2, dishId);
            ResultSet set = statement.executeQuery();
            result = set.next();
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method isDishFromMenu: " + e);
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Integer> getAllDishes(int menuId) {
        List<Integer> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"MENULIST\" WHERE \"ID_MENU\"=?")) {
            statement.setInt(1, menuId);
            ResultSet set = statement.executeQuery();
            while (set.next()){
                result.add(set.getInt("ID_DISH"));
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method getAllDishes: " + e);
            throw new RuntimeException(e);
        }
        return result;
    }
}
