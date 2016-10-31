package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.core.Dish;
import com.goit.homeworks.restaurant.dao.DishDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

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
        dao = new JdbcDishDao(db);
        newDish = new Dish();
        newDish.setId(2);
        newDish.setCategoryId(1);
        newDish.setPrice(10);
        newDish.setWeight(100);
        newDish.setName("CoCoJambo");

        existDish = new Dish();
        existDish .setId(1);
        existDish .setCategoryId(1);
        existDish .setPrice(100);
        existDish .setWeight(250);
        existDish.setName("Chicken");
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
        assertThat("ID_CATEGORY is not equal", ((Integer) rows.get(0).get("ID_CATEGORY")), equalTo(newDish.getCategoryId()));
        assertThat("PRICE is not equal", ((Integer) rows.get(0).get("PRICE")), equalTo(newDish.getPrice()));
        assertThat("WEIGHT is not equal", ((Integer) rows.get(0).get("WEIGHT")), equalTo(newDish.getWeight()));
        assertThat("NAME DISH is not equal", ((String) rows.get(0).get("NAME")).trim(), equalTo(newDish.getName()));

        assertThat("Result dish and test dish not equal", resultDish, equalTo(newDish));
        assertThat("Result dish has wrong ID", resultDish.getId(), equalTo(2));
    }


    @Test
    public void removeTrueDish(){
        assertThat(dao.remove(existDish), equalTo(1));
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_STATEMENT);
        assertThat(rows, hasSize(0));
    }

    @Test
    public void testUpdateDish(){
        newDish.setId(1);
        assertThat(dao.update(newDish), equalTo(1));

        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM DISHES WHERE ID=1");
        assertThat("Row not inserted or not exists", rows, hasSize(1));

        assertThat("ID is not equal", ((Integer) rows.get(0).get("ID")), equalTo(newDish.getId()));
        assertThat("ID_CATEGORY is not equal", ((Integer) rows.get(0).get("ID_CATEGORY")), equalTo(newDish.getCategoryId()));
        assertThat("PRICE is not equal", ((Integer) rows.get(0).get("PRICE")), equalTo(newDish.getPrice()));
        assertThat("WEIGHT is not equal", ((Integer) rows.get(0).get("WEIGHT")), equalTo(newDish.getWeight()));
        assertThat("NAME DISH is not equal", ((String) rows.get(0).get("NAME")).trim(), equalTo(newDish.getName()));

    }

    @Test
    public void testUpdateFakeDish(){
        assertThat(dao.update(newDish), equalTo(0));
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


}