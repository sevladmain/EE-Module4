package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.dao.OrderPreparedDishDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by SeVlad on 02.11.2016.
 */
public class JdbcOrderPreparedDishDaoTest {
    private EmbeddedDatabase db;
    private JdbcTemplate jdbcTemplate;

    private final static String SQL_SELECT_STATEMENT = "SELECT * FROM ORDER_PREPARED_DISHES";
    private OrderPreparedDishDao dao;
    private int orderId;
    private int existingPrepDishId1;
    private int existingPrepDishId2;
    private int newPrepDishId;

    @Before
    public void setUp() {
        db = new EmbeddedDatabaseBuilder()
                .addScript("scheme.sql")
                .build();
        jdbcTemplate = new JdbcTemplate(db);
        dao = new JdbcOrderPreparedDishDao(db);
        orderId = 1;
        existingPrepDishId1 = 2;
        existingPrepDishId2 = 3;
        newPrepDishId = 4;
    }

    @After
    public void tearDown() throws Exception {
        db.shutdown();
    }

    @Test
    public void addPreparedDishToOrderTest() {
        assertThat(dao.addPreparedDishToOrder(newPrepDishId, orderId), equalTo(1));
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM ORDER_PREPARED_DISHES WHERE ID_ORDER=" + orderId + " AND ID_PREPARED_DISH=" + newPrepDishId);
        assertThat("Row not inserted or not exists", rows, hasSize(1));

        assertThat(((Integer) rows.get(0).get("ID_ORDER")), equalTo(orderId));
        assertThat(((Integer) rows.get(0).get("ID_PREPARED_DISH")), equalTo(newPrepDishId));
    }

    @Test
    public void removePreparedDishFromOrderTest() {
        assertThat(dao.removePreparedDishFromOrder(existingPrepDishId1, orderId), equalTo(1));
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM ORDER_PREPARED_DISHES WHERE ID_ORDER=" + orderId + " AND ID_PREPARED_DISH=" + existingPrepDishId1);
        assertThat("Row not inserted or not exists", rows, hasSize(0));
    }

    @Test
    public void isPreparedDishFromOrderTest() {
        assertThat(dao.isPreparedDishFromOrder(existingPrepDishId1, orderId), is(true));
        assertThat(dao.isPreparedDishFromOrder(existingPrepDishId2, orderId), is(true));
        assertThat(dao.isPreparedDishFromOrder(newPrepDishId, orderId), is(false));
    }

    @Test
    public void getAllPreparedDishTest() {
        List<Integer> list = dao.getAllPreparedDish(orderId);
        assertThat(list, hasSize(2));
        assertThat(list, contains(existingPrepDishId1, existingPrepDishId2));
    }

}