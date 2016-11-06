package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.core.Category;
import com.goit.homeworks.restaurant.dao.CategoryDao;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SeVlad on 29.10.2016.
 */
public class JdbcCategoryDao implements CategoryDao {
    private DataSource dataSource;
    private static final Logger LOGGER = Logger.getLogger(JdbcCategoryDao.class);

    public JdbcCategoryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public JdbcCategoryDao() {
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Category create(Category item) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO \"CATEGORY\" (\"NAME\")  VALUES (?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getName());
            statement.executeUpdate();
            ResultSet set = statement.getGeneratedKeys();
            if (set.next()) {
                item.setId(set.getInt("ID"));
            } else {
                LOGGER.error("Unknown Error in create Category: " + item);
                throw new RuntimeException("Unknown Error in create Category");
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method create Category: " + e);
            throw new RuntimeException(e);
        }
        return item;

    }

    @Override
    public int remove(Category item) {
        int result = 0;
        if (item.getId() > 0) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement("DELETE FROM \"CATEGORY\" WHERE \"ID\"=?")) {
                statement.setInt(1, item.getId());
                result = statement.executeUpdate();
            } catch (SQLException e) {
                LOGGER.error("Exception while connecting to DB in method remove Category: " + item + " " + e);
                throw new RuntimeException(e);
            }
        }
        return result;

    }

    @Override
    public int update(Category item) {
        int result = 0;
        if (item.getId() > 0) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement("UPDATE \"CATEGORY\" SET \"NAME\"=? WHERE ID=?")) {
                statement.setString(1, item.getName());
                statement.setInt(2, item.getId());
                result = statement.executeUpdate();
            } catch (SQLException e) {
                LOGGER.error("Exception while connecting to DB in method update Category: " + item + e);
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    @Override
    public List<Category> getAll() {
        List<Category> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM \"CATEGORY\"");

            while (resultSet.next()) {
                Category category = extractCategory(resultSet);
                result.add(category);
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method getAllCategories: " + e);
            throw new RuntimeException(e);
        }
        return result;

    }
    private Category extractCategory(ResultSet set) throws SQLException {
        Category category = new Category();
        category.setId(set.getInt("ID"));
        category.setName(set.getString("NAME").trim());
        return category;
    }

    @Override
    public Category findCategoryById(int id) {
        Category category = new Category();
        if (id > 0) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"CATEGORY\" WHERE \"ID\"=?")) {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()) {
                    category = extractCategory(resultSet);
                }
            } catch (SQLException e) {
                LOGGER.error("Exception while connecting to DB in method findCategoryByID: " + e);
                throw new RuntimeException(e);
            }

        }
        return category;
    }
}
