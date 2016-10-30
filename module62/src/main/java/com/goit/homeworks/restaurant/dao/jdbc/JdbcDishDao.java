package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.core.Dish;
import com.goit.homeworks.restaurant.core.Ingredient;
import com.goit.homeworks.restaurant.dao.CategoryDao;
import com.goit.homeworks.restaurant.dao.DishDao;
import com.goit.homeworks.restaurant.dao.EmployeeDao;
import com.goit.homeworks.restaurant.dao.IngredientDao;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SeVlad on 29.10.2016.
 */
public class JdbcDishDao implements DishDao {
    private DataSource dataSource;
    private CategoryDao categoryDao;
    private IngredientDao ingredientDao;
    private EmployeeDao employeeDao;

    public CategoryDao getCategoryDao() {
        return categoryDao;
    }

    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public IngredientDao getIngredientDao() {
        return ingredientDao;
    }

    public void setIngredientDao(IngredientDao ingredientDao) {
        this.ingredientDao = ingredientDao;
    }

    private static final Logger LOGGER = Logger.getLogger(JdbcDishDao.class);

    public EmployeeDao getEmployeeDao() {
        return employeeDao;
    }

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public JdbcDishDao(DataSource dataSource, CategoryDao categoryDao, IngredientDao ingredientDao, EmployeeDao employeeDao) {
        this.dataSource = dataSource;
        this.categoryDao = categoryDao;
        this.ingredientDao = ingredientDao;
        this.employeeDao = employeeDao;
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
             PreparedStatement statement = connection.prepareStatement("INSERT INTO DISHES (ID_CATEGORY, PRICE, WEIGHT, ISPREPARED, ID_EMPLOYEE_PREPARED, NAME)  VALUES (?, ?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS);
             PreparedStatement updateStatement = connection.prepareStatement("INSERT INTO INGREDIENTLIST(ID_INGREDIENT, ID_DISH) VALUES (?,?)")) {
            statement.setInt(1, item.getCategory().getId());
            statement.setInt(2, item.getPrice());
            statement.setInt(3, item.getWeight());
            statement.setBoolean(4, item.isPrepared());
            statement.setInt(5, item.getWhoPrepared().getId());
            statement.setString(6, item.getName());
            statement.executeUpdate();
            ResultSet set = statement.getGeneratedKeys();
            if (set.next()) {
                item.setId(set.getInt("ID"));
            } else {
                LOGGER.error("Unknown Error in create Dish: " + item);
                throw new RuntimeException("Unknown Error in create Dish");
            }
            for (Ingredient ingredient :
                    item.getIngredientList()) {
                updateStatement.setInt(1, ingredient.getId());
                updateStatement.setInt(2, item.getId());
                updateStatement.executeUpdate();
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
                 PreparedStatement statement = connection.prepareStatement("DELETE FROM DISHES WHERE ID=?");
                 PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM INGREDIENTLIST WHERE ID_DISH=?")) {
                statement.setInt(1, item.getId());
                deleteStatement.setInt(1, item.getId());
                result = statement.executeUpdate();
                result += deleteStatement.executeUpdate();
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
                 PreparedStatement statement = connection.prepareStatement("UPDATE DISHES SET ID_CATEGORY=?, PRICE=?, WEIGHT=?, ISPREPARED=?, ID_EMPLOYEE_PREPARED=?, NAME=? WHERE ID=?");
                 PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM INGREDIENTLIST WHERE ID_DISH=?");
                 PreparedStatement updateStatement = connection.prepareStatement("INSERT INTO INGREDIENTLIST(ID_INGREDIENT, ID_DISH) VALUES (?,?)")) {
                statement.setInt(1, item.getCategory().getId());
                statement.setInt(2, item.getPrice());
                statement.setInt(3, item.getWeight());
                statement.setBoolean(4, item.isPrepared());
                statement.setInt(5, item.getWhoPrepared().getId());
                statement.setString(6, item.getName());
                statement.setInt(7, item.getId());

                deleteStatement.setInt(1, item.getId());
                result = statement.executeUpdate();
                deleteStatement.executeUpdate();
                if(result > 0) {
                    for (Ingredient ingredient :
                            item.getIngredientList()) {
                        updateStatement.setInt(1, ingredient.getId());
                        updateStatement.setInt(2, item.getId());
                        updateStatement.executeUpdate();
                    }
                }
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
             Statement statement = connection.createStatement();
            PreparedStatement ingredientStatement = connection.prepareStatement("SELECT * FROM INGREDIENTLIST WHERE ID_DISH=?")) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM DISHES");
            while (resultSet.next()) {
                Dish dish = extractDish(resultSet);
                dish.setIngredientList(getIngredients(ingredientStatement, dish));
                result.add(dish);
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method getAllCategories: " + e);
            throw new RuntimeException(e);
        }
        return result;
    }

    private List<Ingredient> getIngredients(PreparedStatement ingredientStatement, Dish dish) throws SQLException {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientStatement.setInt(1, dish.getId());
        ResultSet ingrSets = ingredientStatement.executeQuery();
        while(ingrSets.next()){
            ingredients.add(ingredientDao.findIngredientById(ingrSets.getInt("ID_INGREDIENT")));
        }
        return ingredients;
    }

    private Dish extractDish(ResultSet set) throws SQLException {
        Dish dish = new Dish();
        dish.setId(set.getInt("ID"));
        dish.setCategory(categoryDao.findCategoryById(set.getInt("ID_CATEGORY")));
        dish.setPrice(set.getInt("PRICE"));
        dish.setWeight(set.getInt("WEIGHT"));
        dish.setPrepared(set.getBoolean("ISPREPARED"));
        dish.setWhoPrepared(employeeDao.findEmployeeById(set.getInt("ID_EMPLOYEE_PREPARED")));
        dish.setName(set.getString("NAME").trim());
        return dish;
    }

    @Override
    public List<Dish> findDishByName(String name) {
        List<Dish> dishes = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM DISHES WHERE DISHES.NAME LIKE ?");
             PreparedStatement ingredientStatement = connection.prepareStatement("SELECT * FROM INGREDIENTLIST WHERE ID_DISH=?")) {
            statement.setString(1, "%" + name + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                    Dish dish = extractDish(resultSet);
                    dish.setIngredientList(getIngredients(ingredientStatement, dish));
                    dishes.add(dish);
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method getAllEmployees: " + e);
            throw new RuntimeException(e);
        }
        return dishes;
    }

    @Override
    public Dish findDishById(int id) {
        Dish dish = new Dish();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM DISHES WHERE DISHES.ID =?");
             PreparedStatement ingredientStatement = connection.prepareStatement("SELECT * FROM INGREDIENTLIST WHERE ID_DISH=?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                dish = extractDish(resultSet);
                dish.setIngredientList(getIngredients(ingredientStatement, dish));
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method getAllEmployees: " + e);
            throw new RuntimeException(e);
        }
        return dish;
    }

    @Override
    public List<Dish> getAllPreparedDishes() {
        List<Dish> dishes = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             PreparedStatement ingredientStatement = connection.prepareStatement("SELECT * FROM INGREDIENTLIST WHERE ID_DISH=?")) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM DISHES WHERE DISHES.ISPREPARED=TRUE");
            while (resultSet.next()) {
                Dish dish = extractDish(resultSet);
                dish.setIngredientList(getIngredients(ingredientStatement, dish));
                dishes.add(dish);
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method getAllEmployees: " + e);
            throw new RuntimeException(e);
        }
        return dishes;
    }
}
