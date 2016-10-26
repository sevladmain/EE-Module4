package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.core.*;
import com.goit.homeworks.restaurant.dao.EmployeeDao;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.*;
;


/**
 * Created by SeVlad on 23.10.2016.
 */
public class JdbcEmployeeDao implements EmployeeDao {
    private DataSource dataSource;
    private static final Logger LOGGER = Logger.getLogger(JdbcEmployeeDao.class);

    public DataSource getDataSource() {
        return dataSource;
    }

    public JdbcEmployeeDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public JdbcEmployeeDao() {
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public List<Employee> getAll() {
        List<Employee> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM EMPLOYEE");

            while (resultSet.next()) {
                Employee employee = extractEmployee(resultSet);
                result.add(employee);
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method getAllEmployees: " + e);
            throw new RuntimeException(e);
        }
        return result;
    }

    private Employee extractEmployee(ResultSet set) throws SQLException {
        Employee employee = new Employee();
        employee.setId(set.getInt("ID"));
        employee.setFirstName(set.getString("FIRST_NAME").trim());
        employee.setLastName(set.getString("LAST_NAME").trim());
        employee.setDateBirth(set.getDate("DATE_BIRTH"));
        employee.setPosition(getPositionByID(set.getInt("ID_POSITION")));
        employee.setSalary(set.getInt("SALARY"));
        return employee;
    }

    public Position getPositionByID(int ID) {
        Position position = new Position();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM POSITIONS WHERE ID=?")) {
            statement.setInt(1, ID);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                position.setId(set.getInt("ID"));
                position.setPosition(set.getString("POSITION"));
            } else {
                LOGGER.error("Unknown Position ID " + ID);
                throw new RuntimeException("Unknown Position ID " + ID);
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while connecting to DB in method getPositionByID: " + e);
            throw new RuntimeException(e);
        }
        return position;
    }

    @Override
    public Employee create(Employee item) {
        if (!(item.getId() > 0 && item.equals(findEmployeeById(item.getId())))) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement("INSERT INTO EMPLOYEE (FIRST_NAME, LAST_NAME, DATE_BIRTH, ID_POSITION, SALARY)  VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, item.getFirstName());
                statement.setString(2, item.getLastName());
                statement.setDate(3, item.getDateBirth());
                statement.setInt(4, item.getPosition().getId());
                statement.setInt(5, item.getSalary());
                statement.executeUpdate();
                ResultSet set = statement.getGeneratedKeys();
                if (set.next()) {
                    item.setId(set.getInt("ID"));
                } else {
                    LOGGER.error("Unknown Error in create Employee");
                    throw new RuntimeException("Unknown Error in create Employee");
                }
            } catch (SQLException e) {
                LOGGER.error("Exception while connecting to DB in method create Employee: " + e);
                throw new RuntimeException(e);
            }
        }
        return item;
    }

    @Override
    public boolean remove(Employee item) {
        if (item.getId() > 0 && item.equals(findEmployeeById(item.getId()))) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement("DELETE FROM EMPLOYEE WHERE ID=?")) {
                statement.setInt(1, item.getId());
                statement.executeUpdate();
            } catch (SQLException e) {
                LOGGER.error("Exception while connecting to DB in method remove Employee: " + e);
                throw new RuntimeException(e);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int update(Employee item) {
        int result = 0;
        if (item.getId() > 0) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement("UPDATE EMPLOYEE SET FIRST_NAME=?, LAST_NAME=?, DATE_BIRTH=?, ID_POSITION=?, SALARY=? WHERE ID=?")) {
                statement.setString(1, item.getFirstName());
                statement.setString(2, item.getLastName());
                statement.setDate(3, (Date) item.getDateBirth());
                statement.setInt(4, item.getPosition().getId());
                statement.setInt(5, item.getSalary());
                statement.setInt(6, item.getId());
                result = statement.executeUpdate();
            } catch (SQLException e) {
                LOGGER.error("Exception while connecting to DB in method update Employee: " + e);
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    @Override
    public List<Employee> findEmployeeByName(String name) {
        return null;
    }

    public Employee findEmployeeById(int id) {
        return new Employee();
    }

}
