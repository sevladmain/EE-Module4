package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.model.PreparedDish;
import com.goit.homeworks.restaurant.dao.PreparedDishDao;
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
        dao = new JdbcPreparedDishDao(db);

        int employee = 1;
        int dish = 1;

        existingDish = new PreparedDish(dish, employee, 1, false);
        existingDish.setId(1);

        newDish = new PreparedDish(dish+1, employee, 1, true);
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
        assertThat("ID DISH is not equal", ((Integer) rows.get(0).get("ID_DISH")), equalTo(newDish.getDishId()));
        assertThat("ID EMPOYEE is not equal", ((Integer) rows.get(0).get("ID_EMPLOYEE")), equalTo(newDish.getEmployeeId()));
        assertThat("IS PREPARED is not equal", ((Boolean) rows.get(0).get("IS_PREPARED")), equalTo(newDish.isPrepared()));
        assertThat("IS ORDER is not equal", ((Integer) rows.get(0).get("ID_ORDER")), equalTo(newDish.getOrderId()));
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
        assertThat("ID EMPLOYEE is not equal", ((Integer) rows.get(0).get("ID_EMPLOYEE")), equalTo(newDish.getEmployeeId()));
        assertThat("ID DISH is not equal", ((Integer) rows.get(0).get("ID_DISH")), equalTo(newDish.getDishId()));
        assertThat("IS PREPARED is not equal", ((Boolean) rows.get(0).get("IS_PREPARED")), equalTo(newDish.isPrepared()));
        assertThat("IS ORDER is not equal", ((Integer) rows.get(0).get("ID_ORDER")), equalTo(newDish.getOrderId()));
    }

    @Test
    public  void getAllPositionsTest(){
        List<PreparedDish> preparedDishes = dao.getAll();
        assertThat(preparedDishes, hasSize(1));
        assertThat(preparedDishes.get(0), equalTo(existingDish));
    }

    @Test
    public void testFindFakePreparedDishByID(){
        PreparedDish preparedDish = dao.findPreparedDishById(2);
        assertThat(preparedDish, equalTo(new PreparedDish()));
    }

    @Test
    public void testFindPreparedDishByID(){
        PreparedDish preparedDish = dao.findPreparedDishById(1);
        assertThat(preparedDish, equalTo(existingDish));
    }

    @Test
    public void testFindPreparedDishes(){
        List<PreparedDish> preparedDishes = dao.findPreparedDishes();
        assertThat(preparedDishes, hasSize(0));
    }

}