package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.core.*;
import com.goit.homeworks.restaurant.dao.MenuDao;
import com.goit.homeworks.restaurant.dao.PreparedDishDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

/**
 * Created by SeVlad on 30.10.2016.
 */
public class JdbcPreparedDishDaoTest {
    private EmbeddedDatabase db;
    private JdbcTemplate jdbcTemplate;

    private final static String SQL_SELECT_STATEMENT = "SELECT * FROM PREPARED_DISHES";
    private PreparedDishDao dao;
    private PreparedDish existingDish;
    private PreparedDish newDish;

    @Before
    public void setUp() {
        db = new EmbeddedDatabaseBuilder()
                .addScript("scheme.sql")
                .build();
        jdbcTemplate = new JdbcTemplate(db);
        JdbcCategoryDao categoryDao = new JdbcCategoryDao(db);
        JdbcIngredientDao ingredientDao = new JdbcIngredientDao(db);
        JdbcDishDao dishDao = new JdbcDishDao(db, categoryDao, ingredientDao);
        JdbcPositionDao positionDao = new JdbcPositionDao(db);
        JdbcEmployeeDao employeeDao = new JdbcEmployeeDao(db, positionDao);
        dao = new JdbcPreparedDishDao(db, dishDao, employeeDao);

        Position position = new Position(2, "NEWBIE");
        Employee employee = new Employee(1, "Mary", "Ivanova", Date.valueOf("1998-08-12"), position, 1000);

        Dish dish;
        dish = new Dish();
        dish.setId(1);
        dish.setCategory(new Category(1, "SOUPS"));
        dish.setPrice(100);
        dish.setWeight(250);
        dish.setName("Chicken");
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient(1, "Potato", 2));
        ingredients.add(new Ingredient(2, "Tomato", 3));
        dish.setIngredientList(ingredients);

        existingDish = new PreparedDish(dish, employee, false);
        existingDish.setId(1);

        dish = new Dish();
        dish.setId(1);
        dish.setCategory(new Category(1, "SOUPS"));
        dish.setPrice(100);
        dish.setWeight(250);
        dish.setName("Chicken");
        ingredients = new ArrayList<>();
        ingredients.add(new Ingredient(1, "Potato", 2));

        newDish = new PreparedDish(dish, employee, true);
    }

    @After
    public void tearDown() throws Exception {
        db.shutdown();
    }
    @Test
    public void createPreparedDish() throws Exception {
        PreparedDish resultDish = dao.create(newDish);
        newDish.setId(2);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM PREPARED_DISHES WHERE ID=2");
        assertThat("Row not inserted or not exists", rows, hasSize(1));

        assertThat("ID is not equal", ((Integer) rows.get(0).get("ID")), equalTo(newDish.getId()));
        assertThat("ID DISH is not equal", ((Integer) rows.get(0).get("ID_DISH")), equalTo(newDish.getDish().getId()));
        assertThat("ID EMPOYEE is not equal", ((Integer) rows.get(0).get("ID_EMPLOYEE")), equalTo(newDish.getEmployee().getId()));
        assertThat("IS PREPARED is not equal", ((Boolean) rows.get(0).get("IS_PREPARED")), equalTo(newDish.isPrepared()));
    }

    @Test
    public void removeTruePreparedOrder(){
        assertThat(dao.remove(existingDish), equalTo(1));
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_STATEMENT);
        assertThat(rows, hasSize(0));
    }
    @Test
    public void testUpdatePreparedOrder(){
        newDish.setId(1);
        assertThat(dao.update(newDish), equalTo(1));

        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM PREPARED_DISHES WHERE ID=1");
        assertThat("Row not inserted or not exists", rows, hasSize(1));

        assertThat("ID is not equal", ((Integer) rows.get(0).get("ID")), equalTo(newDish.getId()));
        assertThat("ID EMPLOYEE is not equal", ((Integer) rows.get(0).get("ID_EMPLOYEE")), equalTo(newDish.getEmployee().getId()));
        assertThat("ID DISH is not equal", ((Integer) rows.get(0).get("ID_DISH")), equalTo(newDish.getDish().getId()));
        assertThat("IS PREPARED is not equal", ((Boolean) rows.get(0).get("IS_PREPARED")), equalTo(newDish.isPrepared()));
    }



}