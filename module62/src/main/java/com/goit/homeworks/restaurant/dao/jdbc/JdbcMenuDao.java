package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.core.Menu;
import com.goit.homeworks.restaurant.dao.MenuDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SeVlad on 30.10.2016.
 */
public class JdbcMenuDao implements MenuDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcMenuDao.class);
    private DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public JdbcMenuDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Menu create(Menu item) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO \"MENU\" (\"NAME\")  VALUES (?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getName());
            statement.executeUpdate();
            ResultSet set = statement.getGeneratedKeys();
            if (set.next()) {
                item.setId(set.getInt("ID"));
            } else {
                LOGGER.error("Unknown Error in create Menu: " + item);
                throw new RuntimeException("Unknown Error in create Menu");
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
                 PreparedStatement statement = connection.prepareStatement("DELETE FROM \"MENU\" WHERE \"ID\"=?")) {
                statement.setInt(1, item.getId());
                result = statement.executeUpdate();
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
                 PreparedStatement statement = connection.prepareStatement("UPDATE \"MENU\" SET \"NAME\"=? WHERE \"ID\"=?")) {
                statement.setString(1, item.getName());
                statement.setInt(2, item.getId());
                result = statement.executeUpdate();
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
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM \"MENU\"");
            while (resultSet.next()) {
                Menu menu = extractMenu(resultSet);
                result.add(menu);
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method getAllMenu: " + e);
            throw new RuntimeException(e);
        }
        return result;
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
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"MENU\" WHERE \"NAME\" LIKE ?")) {
            statement.setString(1, "%" + name + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Menu menu = extractMenu(resultSet);
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
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"MENU\" WHERE \"ID\" =?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                menu = extractMenu(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method find menu by id: " + id + e);
            throw new RuntimeException(e);
        }
        return menu;
    }
}
