package com.goit.homeworks.restaurant.dao.jdbc;


import com.goit.homeworks.restaurant.model.Position;
import com.goit.homeworks.restaurant.dao.PositionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SeVlad on 27.10.2016.
 */
public class JdbcPositionDao implements PositionDao {
    private DataSource dataSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcPositionDao.class);

    public DataSource getDataSource() {
        return dataSource;
    }

    public JdbcPositionDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public Position create(Position item) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO \"POSITIONS\" (\"POSITION\")  VALUES (?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getPosition());
            statement.executeUpdate();
            ResultSet set = statement.getGeneratedKeys();
            if (set.next()) {
                item.setId(set.getInt("ID"));
            } else {
                LOGGER.error("Unknown Error in create Position: " + item.getPosition());
                throw new RuntimeException("Unknown Error in create Position");
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method create Position: " + e);
            throw new RuntimeException(e);
        }
        return item;
    }

    @Override
    public int remove(Position item) {
        int result = 0;
        if (item.getId() > 0) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement("DELETE FROM \"POSITIONS\" WHERE \"ID\"=?")) {
                statement.setInt(1, item.getId());
                result = statement.executeUpdate();
            } catch (SQLException e) {
                LOGGER.error("Exception while connecting to DB in method remove Postition: " + item + " " + e);
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    @Override
    public int update(Position item) {
        int result = 0;
        if (item.getId() > 0) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement("UPDATE \"POSITIONS\" SET \"POSITION\"=? WHERE \"ID\"=?")) {
                statement.setString(1, item.getPosition());
                statement.setInt(2, item.getId());
                result = statement.executeUpdate();
            } catch (SQLException e) {
                LOGGER.error("Exception while connecting to DB in method update Position: " + item + e);
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    @Override
    public List<Position> getAll() {

        List<Position> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM \"POSITIONS\"");

            while (resultSet.next()) {
                Position position = extractPosition(resultSet);
                result.add(position);
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method getAllPositions: " + e);
            throw new RuntimeException(e);
        }
        return result;

    }

    private Position extractPosition(ResultSet set) throws SQLException {
        Position position = new Position();
        position.setId(set.getInt("ID"));
        position.setPosition(set.getString("POSITION").trim());
        return position;
    }

    @Override
    public Position findPositionById(int id) {
        Position position = new Position();
        if (id > 0) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"POSITIONS\" WHERE \"ID\"=?")) {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()) {
                    position = extractPosition(resultSet);
                }
            } catch (SQLException e) {
                LOGGER.error("Exception while connecting to DB in method remove Employee: " + e);
                throw new RuntimeException(e);
            }

        }
        return position;
    }
}
