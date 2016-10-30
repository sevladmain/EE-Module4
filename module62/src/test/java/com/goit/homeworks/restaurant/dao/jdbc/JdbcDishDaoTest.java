package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.core.*;
import com.goit.homeworks.restaurant.dao.DishDao;
import com.goit.homeworks.restaurant.dao.PositionDao;
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
 * Created by SeVlad on 29.10.2016.
 */
public class JdbcDishDaoTest {
    private EmbeddedDatabase db;
    private JdbcTemplate jdbcTemplate;

    private final static String SQL_SELECT_STATEMENT = "SELECT * FROM DISHES";
    private DishDao dao;
    private Dish newDish;
    private Dish existDish;

    @Before
    public void setUp() {
        db = new EmbeddedDatabaseBuilder()
                .addScript("scheme.sql")
                .build();
        jdbcTemplate = new JdbcTemplate(db);
        JdbcPositionDao positionDao = new JdbcPositionDao(db);
        JdbcEmployeeDao employeeDao = new JdbcEmployeeDao(db, positionDao);
        JdbcCategoryDao categoryDao = new JdbcCategoryDao(db);
        JdbcIngredientDao ingredientDao = new JdbcIngredientDao(db);
        dao = new JdbcDishDao(db, categoryDao, ingredientDao, employeeDao);
        newDish = new Dish();
        newDish.setId(2);
        newDish.setCategory(new Category(1, "SOUPS"));
        newDish.setPrice(10);
        newDish.setWeight(100);
        newDish.setName("CoCoJambo");
        newDish.setPrepared(true);
        Ingredient ingredient = new Ingredient(2, "Tomato", 100);
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient);
        Position position = new Position(2, "NEWBIE");
        newDish.setWhoPrepared(new Employee(1, "Mary", "Ivanova", Date.valueOf("1998-08-12"), position, 1000));
        newDish.setIngredientList(ingredients);

        existDish = new Dish();
        existDish .setId(1);
        existDish .setCategory(new Category(1, "SOUPS"));
        existDish .setPrice(100);
        existDish .setWeight(250);
        existDish .setPrepared(true);
        existDish.setName("Chicken");
        ingredients = new ArrayList<>();
        ingredients.add(new Ingredient(1, "Potato", 2));
        ingredients.add(new Ingredient(2, "Tomato", 3));
        position = new Position(2, "NEWBIE");
        existDish .setWhoPrepared(new Employee(1, "Mary", "Ivanova", Date.valueOf("1998-08-12"), position, 1000));
        existDish .setIngredientList(ingredients);
    }

    @After
    public void tearDown() throws Exception {
        db.shutdown();
    }
    @Test
    public void createDish() throws Exception {
        Dish resultDish = dao.create(newDish);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM DISHES WHERE ID=2");
        assertThat("Row not inserted or not exists", rows, hasSize(1));

        assertThat("ID is not equal", ((Integer) rows.get(0).get("ID")), equalTo(newDish.getId()));
        assertThat("ID_CATEGORY is not equal", ((Integer) rows.get(0).get("ID_CATEGORY")), equalTo(newDish.getCategory().getId()));
        assertThat("PRICE is not equal", ((Integer) rows.get(0).get("PRICE")), equalTo(newDish.getPrice()));
        assertThat("ID_CATEGORY is not equal", ((Integer) rows.get(0).get("WEIGHT")), equalTo(newDish.getWeight()));
        assertThat("ISPREPERED is not equal", ((Boolean) rows.get(0).get("ISPREPARED")), equalTo(newDish.isPrepared()));
        assertThat("ID_EMPLOYEE is not equal", ((Integer) rows.get(0).get("ID_EMPLOYEE_PREPARED")), equalTo(newDish.getWhoPrepared().getId()));
        assertThat("NAME DISH is not equal", ((String) rows.get(0).get("NAME")).trim(), equalTo(newDish.getName()));
        rows = jdbcTemplate.queryForList("SELECT * FROM INGREDIENTLIST WHERE ID_DISH=2");
        assertThat(rows, hasSize(1));

        assertThat("INGREDIENTLIST is not inserted", ((Integer) rows.get(0).get("ID_INGREDIENT")), equalTo(newDish.getIngredientList().get(0).getId()));

        assertThat("Result dish and test dish not equal", resultDish, equalTo(newDish));
        assertThat("Result dish has wrong ID", resultDish.getId(), equalTo(2));
    }


    @Test
    public void removeTrueDish(){
        assertThat(dao.remove(existDish), equalTo(3));
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_STATEMENT);
        assertThat(rows, hasSize(0));
        rows = jdbcTemplate.queryForList("SELECT * FROM INGREDIENTLIST WHERE ID_DISH=" + existDish.getId());
        assertThat(rows, hasSize(0));
    }

    @Test
    public void testUpdateDish(){
        newDish.setId(1);
        assertThat(dao.update(newDish), equalTo(1));

        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM DISHES WHERE ID=1");
        assertThat("Row not inserted or not exists", rows, hasSize(1));

        assertThat("ID is not equal", ((Integer) rows.get(0).get("ID")), equalTo(newDish.getId()));
        assertThat("ID_CATEGORY is not equal", ((Integer) rows.get(0).get("ID_CATEGORY")), equalTo(newDish.getCategory().getId()));
        assertThat("PRICE is not equal", ((Integer) rows.get(0).get("PRICE")), equalTo(newDish.getPrice()));
        assertThat("ID_CATEGORY is not equal", ((Integer) rows.get(0).get("WEIGHT")), equalTo(newDish.getWeight()));
        assertThat("ISPREPERED is not equal", ((Boolean) rows.get(0).get("ISPREPARED")), equalTo(newDish.isPrepared()));
        assertThat("ID_EMPLOYEE is not equal", ((Integer) rows.get(0).get("ID_EMPLOYEE_PREPARED")), equalTo(newDish.getWhoPrepared().getId()));
        assertThat("NAME DISH is not equal", ((String) rows.get(0).get("NAME")).trim(), equalTo(newDish.getName()));

        rows = jdbcTemplate.queryForList("SELECT * FROM INGREDIENTLIST WHERE ID_DISH=1");
        assertThat(rows, hasSize(1));

        assertThat("INGREDIENTLIST is not inserted", ((Integer) rows.get(0).get("ID_INGREDIENT")), equalTo(newDish.getIngredientList().get(0).getId()));
    }

    @Test
    public void testUpdateFakeDish(){
        assertThat(dao.update(newDish), equalTo(0));
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM INGREDIENTLIST");
        assertThat(rows, hasSize(2));
    }

    @Test
    public  void getAllDishesTest(){
        List<Dish> dishes = dao.getAll();
        assertThat(dishes, hasSize(1));
        Dish testedDish = dishes.get(0);
        assertThat(testedDish, equalTo(existDish));
    }

    @Test
    public void testFindFakePositionByID(){
        Dish dish = dao.findDishById(2);
        assertThat(dish, equalTo(new Dish()));
    }

    @Test
    public void testFindFakePositionByIllegalID(){
        Dish dish = dao.findDishById(-2);
        assertThat(dish, equalTo(new Dish()));
    }

    @Test
    public void testFindPositionByID(){
        Dish dish = dao.findDishById(1);
        assertThat(dish, equalTo(existDish));
    }

    @Test
    public void findDishByName() throws Exception {
        List<Dish> dishes = dao.findDishByName("ick");
        assertThat(dishes, hasSize(1));
        assertThat(dishes.get(0), equalTo(existDish));
    }

    @Test
    public void getAllPreparedDishes() throws Exception {
        List<Dish> dishes = dao.getAllPreparedDishes();
        assertThat(dishes, hasSize(1));
        assertThat(dishes.get(0), equalTo(existDish));

    }

}