package com.goit.homeworks.restaurant.dao.jdbc;

import com.goit.homeworks.restaurant.model.Order;
import com.goit.homeworks.restaurant.dao.OrderDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

/**
 * Created by SeVlad on 01.11.2016.
 */
public class JdbcOrderDaoTest {
    private EmbeddedDatabase db;
    private JdbcTemplate jdbcTemplate;
    private Order existingOrder1;
    private Order existingOrder2;
    private Order newOrder;

    private final static String SQL_SELECT_STATEMENT = "SELECT * FROM ORDERS";
    private OrderDao dao;

    @Before
    public void setUp() {
        db = new EmbeddedDatabaseBuilder()
                .addScript("scheme.sql")
                .build();
        jdbcTemplate = new JdbcTemplate(db);
        dao = new JdbcOrderDao(db);
        existingOrder1 = new Order(1, 1, 2, Date.valueOf("2016-11-01"), true);
        existingOrder2 = new Order(2, 2, 3, Date.valueOf("2016-11-02"), false);
        newOrder = new Order(3, 5, Date.valueOf("2015-12-02"), false);
    }

    @After
    public void tearDown() throws Exception {
        db.shutdown();
    }

    @Test
    public void create() throws Exception {
        Order resultOrder = dao.create(newOrder);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM ORDERS WHERE ID=3");
        assertThat(rows, hasSize(1));
        assertThat(((Integer) rows.get(0).get("ID")), equalTo(newOrder.getId()));
        assertThat(((Integer) rows.get(0).get("ID_EMP")), equalTo(newOrder.getEmployeeId()));
        assertThat(((Integer) rows.get(0).get("TABLE_NUM")), equalTo(newOrder.getTableNum()));
        assertThat(((Date) rows.get(0).get("DATE")), equalTo(newOrder.getDate()));
        assertThat(((Boolean) rows.get(0).get("ISOPEN")), equalTo(newOrder.isOpen()));

        assertThat(resultOrder, equalTo(newOrder));
        assertThat(resultOrder.getId(), equalTo(3));
    }

    @Test
    public void removeFakeOrder(){

        assertThat(dao.remove(newOrder), equalTo(0));
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_STATEMENT);
        assertThat(rows, hasSize(2));
    }

    @Test
    public void removeTrueOrder(){
        assertThat(dao.remove(existingOrder2), equalTo(1));
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_STATEMENT);
        assertThat(rows, hasSize(1));

        rows = jdbcTemplate.queryForList("SELECT * FROM ORDERS WHERE ID="+existingOrder2.getId());
        assertThat(rows, hasSize(0));
    }

    @Test
    public void testUpdateOrder(){
        newOrder.setId(2);
        assertThat(dao.update(newOrder), equalTo(1));

        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM ORDERS WHERE ID=2");
        assertThat(((Integer) rows.get(0).get("ID")), equalTo(newOrder.getId()));
        assertThat(((Integer) rows.get(0).get("ID_EMP")), equalTo(newOrder.getEmployeeId()));
        assertThat(((Integer) rows.get(0).get("TABLE_NUM")), equalTo(newOrder.getTableNum()));
        assertThat(((Date) rows.get(0).get("DATE")), equalTo(newOrder.getDate()));
        assertThat(((Boolean) rows.get(0).get("ISOPEN")), equalTo(newOrder.isOpen()));

    }

    @Test
    public void testUpdateFakeOrder(){
        newOrder.setId(5);
        assertThat(dao.update(newOrder), equalTo(0));
    }

    @Test
    public  void getAllOrdersTest(){
        List<Order> orders = dao.getAll();
        assertThat(orders, hasSize(2));
        assertThat(orders.get(0), equalTo(existingOrder1));
        assertThat(orders.get(1), equalTo(existingOrder2));
    }
    @Test
    public  void getAllOpenOrdersTest(){
        List<Order> orders = dao.getAllOpenOrders();
        assertThat(orders, hasSize(1));
        assertThat(orders.get(0), equalTo(existingOrder1));
    }
    @Test
    public  void getAllClosedOrdersTest(){
        List<Order> orders = dao.getAllClosedOrders();
        assertThat(orders, hasSize(1));
        assertThat(orders.get(0), equalTo(existingOrder2));
    }

    @Test
    public void testFindFakeOrderByID(){
        Order order = dao.findOrderById(4);
        assertThat(order, equalTo(new Order()));
    }


    @Test
    public void testFindPositionByID(){
        Order order = dao.findOrderById(1);
        assertThat(order, equalTo(existingOrder1));
    }



}