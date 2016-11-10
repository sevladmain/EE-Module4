package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.core.Dish;
import com.goit.homeworks.restaurant.dao.DishDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SeVlad on 29.10.2016.
 */
public class JdbcDishDao implements DishDao {
    private DataSource dataSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcDishDao.class);

    public JdbcDishDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public JdbcDishDao() {
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Dish create(Dish item) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO \"DISHES\" (\"ID_CATEGORY\", \"PRICE\", \"WEIGHT\", \"NAME\")  VALUES (?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, item.getCategoryId());
            statement.setInt(2, item.getPrice());
            statement.setInt(3, item.getWeight());
            statement.setString(4, item.getName());
            statement.executeUpdate();
            ResultSet set = statement.getGeneratedKeys();
            if (set.next()) {
                item.setId(set.getInt("ID"));
            } else {
                LOGGER.error("Unknown Error in create Dish: " + item);
                throw new RuntimeException("Unknown Error in create Dish");
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method create Dish: " + e);
            throw new RuntimeException(e);
        }
        return item;
    }

    @Override
    public int remove(Dish item) {
        int result = 0;
        if (item.getId() > 0) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement("DELETE FROM \"DISHES\" WHERE \"ID\"=?")) {
                statement.setInt(1, item.getId());
                result = statement.executeUpdate();
            } catch (SQLException e) {
                LOGGER.error("Exception while connecting to DB in method remove Dishes: " + item + " " + e);
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    @Override
    public int update(Dish item) {

        int result = 0;
        if (item.getId() > 0) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement("UPDATE \"DISHES\" SET \"ID_CATEGORY\"=?, \"PRICE\"=?, \"WEIGHT\"=?, \"NAME\"=? WHERE \"ID\"=?")) {
                statement.setInt(1, item.getCategoryId());
                statement.setInt(2, item.getPrice());
                statement.setInt(3, item.getWeight());
                statement.setString(4, item.getName());
                statement.setInt(5, item.getId());

                result = statement.executeUpdate();
            } catch (SQLException e) {
                LOGGER.error("Exception while connecting to DB in method update Dish: " + item + e);
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    @Override
    public List<Dish> getAll() {
        List<Dish> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM \"DISHES\"");
            while (resultSet.next()) {
                Dish dish = extractDish(resultSet);
                result.add(dish);
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method getAllCategories: " + e);
            throw new RuntimeException(e);
        }
        return result;
    }

    private Dish extractDish(ResultSet set) throws SQLException {
        Dish dish = new Dish();
        dish.setId(set.getInt("ID"));
        dish.setCategoryId(set.getInt("ID_CATEGORY"));
        dish.setPrice(set.getInt("PRICE"));
        dish.setWeight(set.getInt("WEIGHT"));
        dish.setName(set.getString("NAME").trim());
        return dish;
    }

    @Override
    public List<Dish> findDishByName(String name) {
        List<Dish> dishes = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"DISHES\" WHERE \"NAME\" LIKE ?")) {
            statement.setString(1, "%" + name + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                    Dish dish = extractDish(resultSet);
                    dishes.add(dish);
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method findDishByName: " + e);
            throw new RuntimeException(e);
        }
        return dishes;
    }

    @Override
    public Dish findDishById(int id) {
        Dish dish = new Dish();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"DISHES\" WHERE \"ID\" =?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                dish = extractDish(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method getAllEmployees: " + e);
            throw new RuntimeException(e);
        }
        return dish;
    }
}
