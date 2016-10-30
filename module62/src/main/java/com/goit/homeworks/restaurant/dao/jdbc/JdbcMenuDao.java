package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.core.Dish;
import com.goit.homeworks.restaurant.core.Menu;
import com.goit.homeworks.restaurant.dao.DishDao;
import com.goit.homeworks.restaurant.dao.MenuDao;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SeVlad on 30.10.2016.
 */
public class JdbcMenuDao implements MenuDao {
    private static final Logger LOGGER = Logger.getLogger(JdbcMenuDao.class);
    private DataSource dataSource;
    private DishDao dishDao;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public JdbcMenuDao(DataSource dataSource, DishDao dishDao) {
        this.dataSource = dataSource;
        this.dishDao = dishDao;
    }

    public DishDao getDishDao() {
        return dishDao;
    }

    public void setDishDao(DishDao dishDao) {
        this.dishDao = dishDao;
    }

    @Override
    public Menu create(Menu item) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO MENU (NAME)  VALUES (?)",
                     Statement.RETURN_GENERATED_KEYS);
             PreparedStatement updateStatement = connection.prepareStatement("INSERT INTO MENULIST (ID_MENU, ID_DISH) VALUES (?,?)")) {
            statement.setString(1, item.getName());
            statement.executeUpdate();
            ResultSet set = statement.getGeneratedKeys();
            if (set.next()) {
                item.setId(set.getInt("ID"));
            } else {
                LOGGER.error("Unknown Error in create Menu: " + item);
                throw new RuntimeException("Unknown Error in create Menu");
            }
            for (Dish dish :
                    item.getDishes()) {
                updateStatement.setInt(1, item.getId());
                updateStatement.setInt(2, dish.getId());
                updateStatement.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method create Menu: " + e);
            throw new RuntimeException(e);
        }
        return item;

    }

    @Override
    public int remove(Menu item) {
        int result = 0;
        if (item.getId() > 0) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement("DELETE FROM MENU WHERE ID=?");
                 PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM MENULIST WHERE ID_MENU=?")) {
                statement.setInt(1, item.getId());
                deleteStatement.setInt(1, item.getId());
                result = statement.executeUpdate();
                result += deleteStatement.executeUpdate();
            } catch (SQLException e) {
                LOGGER.error("Exception while connecting to DB in method remove Menu: " + item + " " + e);
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    @Override
    public int update(Menu item) {
        int result = 0;
        if (item.getId() > 0) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement("UPDATE MENU SET NAME=? WHERE ID=?");
                 PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM MENULIST WHERE ID_MENU=?");
                 PreparedStatement updateStatement = connection.prepareStatement("INSERT INTO MENULIST(ID_MENU, ID_DISH) VALUES (?,?)")) {
                statement.setString(1, item.getName());
                statement.setInt(2, item.getId());

                deleteStatement.setInt(1, item.getId());
                result = statement.executeUpdate();
                deleteStatement.executeUpdate();
                if(result > 0) {
                    for (Dish dish :
                            item.getDishes()) {
                        updateStatement.setInt(1, item.getId());
                        updateStatement.setInt(2, dish.getId());
                        updateStatement.executeUpdate();
                    }
                }
            } catch (SQLException e) {
                LOGGER.error("Exception while connecting to DB in method update Menu: " + item + e);
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    @Override
    public List<Menu> getAll() {
        List<Menu> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             PreparedStatement ingredientStatement = connection.prepareStatement("SELECT * FROM MENULIST WHERE ID_MENU=?")) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM MENU");
            while (resultSet.next()) {
                Menu menu = extractMenu(resultSet);
                menu.setDishes(getDishes(ingredientStatement, menu));
                result.add(menu);
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method getAllMenu: " + e);
            throw new RuntimeException(e);
        }
        return result;
    }

    private List<Dish> getDishes(PreparedStatement statement, Menu menu) throws SQLException {
        List<Dish> dishes = new ArrayList<>();
        statement.setInt(1, menu.getId());
        ResultSet ingrSets = statement.executeQuery();
        while(ingrSets.next()){
            dishes.add(dishDao.findDishById(ingrSets.getInt("ID_DISH")));
        }
        return dishes;

    }

    private Menu extractMenu(ResultSet set) throws SQLException {
        Menu menu = new Menu();
        menu.setId(set.getInt("ID"));
        menu.setName(set.getString("NAME").trim());
        return menu;
    }

    @Override
    public List<Menu> findMenuByName(String name){
        List<Menu> menus = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM MENU WHERE MENU.NAME LIKE ?");
             PreparedStatement ingredientStatement = connection.prepareStatement("SELECT * FROM MENULIST WHERE ID_MENU=?")) {
            statement.setString(1, "%" + name + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Menu menu = extractMenu(resultSet);
                menu.setDishes(getDishes(ingredientStatement, menu));
                menus.add(menu);
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method getAllMenu: " + e);
            throw new RuntimeException(e);
        }
        return menus;
    }

    @Override
    public Menu findMenuById(int id) {
        Menu menu = new Menu();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM MENU WHERE MENU.ID =?");
             PreparedStatement ingredientStatement = connection.prepareStatement("SELECT * FROM MENULIST WHERE ID_MENU=?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                menu = extractMenu(resultSet);
                menu.setDishes(getDishes(ingredientStatement, menu));
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method find menu by id: " + id + e);
            throw new RuntimeException(e);
        }
        return menu;
    }
}
