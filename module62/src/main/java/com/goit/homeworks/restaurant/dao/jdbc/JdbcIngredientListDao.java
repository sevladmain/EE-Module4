package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.dao.IngredientListDao;
import org.apache.log4j.Logger;

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
public class JdbcIngredientListDao implements IngredientListDao {
    private DataSource dataSource;
    private static final Logger LOGGER = Logger.getLogger(JdbcIngredientListDao.class);

    public JdbcIngredientListDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int addIngredientToDish(int ingredientId, int dishId) {
        int result = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO \"INGREDIENTLIST\" (\"ID_INGREDIENT\", \"ID_DISH\")  VALUES (?,?)")) {
            statement.setInt(1, ingredientId);
            statement.setInt(2, dishId);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method addIngredientToDish: " + e);
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int removeIngredientFromDish(int ingredientId, int dishId) {
        int result = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM \"INGREDIENTLIST\" WHERE \"ID_INGREDIENT\"=? AND \"ID_DISH\"=?")) {
            statement.setInt(1, ingredientId);
            statement.setInt(2, dishId);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method removeIngredientFromDish: " + e);
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean isIngredientFromDish(int ingredientId, int dishId) {
        boolean result = false;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"INGREDIENTLIST\" WHERE \"ID_INGREDIENT\"=? AND \"ID_DISH\"=?")) {
            statement.setInt(1, ingredientId);
            statement.setInt(2, dishId);
            ResultSet set = statement.executeQuery();
            result = set.next();
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method isIngredientFromDish: " + e);
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Integer> getAllIngredientsIds(int dishId) {
        List<Integer> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"INGREDIENTLIST\" WHERE \"ID_DISH\"=?")) {
            statement.setInt(1, dishId);
            ResultSet set = statement.executeQuery();
            while (set.next()){
                result.add(set.getInt("ID_INGREDIENT"));
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method getAllIngredientsIds: " + e);
            throw new RuntimeException(e);
        }
        return result;
    }
}
