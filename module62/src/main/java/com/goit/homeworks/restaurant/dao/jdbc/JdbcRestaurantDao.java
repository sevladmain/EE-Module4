package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.core.*;
import com.goit.homeworks.restaurant.dao.RestaurantDao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.*;
;


/**
 * Created by SeVlad on 23.10.2016.
 */
public class JdbcRestaurantDao implements RestaurantDao {
    private DataSource dataSource;
    private static final Logger LOGGER = Logger.getLogger(JdbcRestaurantDao.class);

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean add(Dish dishes) {
        return false;
    }

    @Override
    public boolean add(Employee employee) {
        return false;
    }

    @Override
    public boolean add(Menu menu) {
        return false;
    }

    @Override
    public boolean add(Order order) {
        return false;
    }

    @Override
    public boolean add(Ingredient ingredient) {
        return false;
    }

    @Override
    public boolean remove(Dish dishes) {
        return false;
    }

    @Override
    public boolean remove(Employee employee) {
        return false;
    }

    @Override
    public boolean remove(Menu menu) {
        return false;
    }

    @Override
    public boolean remove(Order order) {
        return false;
    }

    @Override
    public boolean remove(Ingredient ingredient) {
        return false;
    }

    @Override
    public boolean update(Dish dishes) {
        return false;
    }

    @Override
    public boolean update(Employee employee) {
        return false;
    }

    @Override
    public boolean update(Menu menu) {
        return false;
    }

    @Override
    public boolean update(Order order) {
        return false;
    }

    @Override
    public boolean update(Ingredient ingredient) {
        return false;
    }

    @Override
    public List<Dish> findDishByName(String name) {
        return null;
    }

    @Override
    public List<Employee> findEmployeeByName(String name) {
        return null;
    }

    @Override
    public List<Menu> findMenuByName(String name) {
        return null;
    }

    @Override
    public List<Ingredient> findIngredientByName(String name) {
        return null;
    }

    @Override
    public List<Dish> getAllDishes() {
        return null;
    }

    @Override
    public List<Dish> getAllPreparedDishes() {
        return null;
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> result = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM EMPLOYEE");

            while(resultSet.next()){
                Employee employee = createEmployee(resultSet);
                result.add(employee);
            }
        }catch (SQLException e){
            LOGGER.error("Exeption while conneting to DB in method getAllEmployees: " + e);
            throw new RuntimeException(e);
        }
        return result;
    }

    private Employee createEmployee(ResultSet set) throws SQLException {
        Employee employee = new Employee();
        employee.setId(set.getInt("ID"));
        employee.setFirstName(set.getString("FIRST_NAME"));
        employee.setLastName(set.getString("LAST_NAME"));
        employee.setDateBirth(set.getDate("DATE_BIRTH"));
        employee.setPosition(getPosittionByID(set.getInt("ID_POSITION")));
        employee.setSalary(set.getInt("SALARY"));
        return employee;
    }
    public Position getPosittionByID(int ID){
        return new Position();
    }
    @Override
    public List<Menu> getAllMenus() {
        return null;
    }

    @Override
    public List<Order> getAllOpenOrders() {
        return null;
    }

    @Override
    public List<Order> getAllClosedOrders() {
        return null;
    }

    @Override
    public List<Ingredient> getAllIngredients() {
        return null;
    }

    @Override
    public List<Ingredient> getAllEndIngredients(int minAmount) {
        return null;
    }
}
