package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.core.Employee;
import com.goit.homeworks.restaurant.core.Ingredient;
import com.goit.homeworks.restaurant.core.Position;
import com.goit.homeworks.restaurant.dao.IngredientDao;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SeVlad on 29.10.2016.
 */
public class JdbcIngredientDao implements IngredientDao {
    private DataSource dataSource;
    private static final Logger LOGGER = Logger.getLogger(JdbcIngredientDao.class);

    public JdbcIngredientDao() {
    }

    public JdbcIngredientDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Ingredient create(Ingredient item) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO INGREDIENTS (NAME, AMOUNT)  VALUES (?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getName());
            statement.setInt(2, item.getAmount());
            statement.executeUpdate();
            ResultSet set = statement.getGeneratedKeys();
            if (set.next()) {
                item.setId(set.getInt("ID"));
            } else {
                LOGGER.error("Unknown Error in create Ingredient: " + item);
                throw new RuntimeException("Unknown Error in create Ingredient");
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method create Ingredient: " + e);
            throw new RuntimeException(e);
        }
        return item;
    }

    @Override
    public int remove(Ingredient item) {
        int result = 0;
        if (item.getId() > 0) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement("DELETE FROM INGREDIENTS WHERE ID=?")) {
                statement.setInt(1, item.getId());
                result = statement.executeUpdate();
            } catch (SQLException e) {
                LOGGER.error("Exception while connecting to DB in method remove Ingredient: " + item + " " + e);
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    @Override
    public int update(Ingredient item) {
        int result = 0;
        if (item.getId() > 0) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement("UPDATE INGREDIENTS SET NAME=?, AMOUNT=? WHERE ID=?")) {
                statement.setString(1, item.getName());
                statement.setInt(2, item.getAmount());
                statement.setInt(3, item.getId());
                result = statement.executeUpdate();
            } catch (SQLException e) {
                LOGGER.error("Exception while connecting to DB in method update Ingredient: " + item + e);
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    @Override
    public List<Ingredient> getAll() {

        List<Ingredient> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM INGREDIENTS");

            while (resultSet.next()) {
                Ingredient ingredient = extractIngredient(resultSet);
                result.add(ingredient);
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method getAllIngredients: " + e);
            throw new RuntimeException(e);
        }
        return result;
    }

    private Ingredient extractIngredient(ResultSet resultSet) throws SQLException {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(resultSet.getInt("ID"));
        ingredient.setName(resultSet.getString("NAME").trim());
        ingredient.setAmount(resultSet.getInt("AMOUNT"));
        return ingredient;
    }

    @Override
    public List<Ingredient> findIngredientByName(String name) {
        List<Ingredient> ingredients = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM INGREDIENTS WHERE NAME LIKE ? ")) {
            statement.setString(1, "%" + name + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Ingredient ingredient = extractIngredient(resultSet);
                ingredients.add(ingredient);
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method find Ingredient by name: " + e);
            throw new RuntimeException(e);
        }
        return ingredients;
    }

    @Override
    public List<Ingredient> getAllEndIngredients(int minAmount) {
        List<Ingredient> ingredients = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM INGREDIENTS WHERE INGREDIENTS.AMOUNT <= ? ")) {
            statement.setInt(1, minAmount);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Ingredient ingredient = extractIngredient(resultSet);
                ingredients.add(ingredient);
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method get all end ingredients: " + e);
            throw new RuntimeException(e);
        }
        return ingredients;
    }

    @Override
    public Ingredient findIngredientById(int id) {
        Ingredient ingredient = new Ingredient();
        if (id > 0) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement("SELECT * FROM INGREDIENTS WHERE ID=?")) {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()) {
                    ingredient = extractIngredient(resultSet);
                }
            } catch (SQLException e) {
                LOGGER.error("Exception while connecting to DB in method remove Employee: " + e);
                throw new RuntimeException(e);
            }
        }
        return ingredient;
    }
}
